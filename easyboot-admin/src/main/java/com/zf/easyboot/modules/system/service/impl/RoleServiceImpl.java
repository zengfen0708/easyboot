package com.zf.easyboot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.*;
import com.zf.easyboot.modules.system.mapper.MenuMapper;
import com.zf.easyboot.modules.system.mapper.PermissionMapper;
import com.zf.easyboot.modules.system.mapper.RoleMapper;
import com.zf.easyboot.modules.system.service.RoleMenuService;
import com.zf.easyboot.modules.system.service.RolePermissionService;
import com.zf.easyboot.modules.system.service.RoleService;
import com.zf.easyboot.modules.system.vo.RoleMenuVo;
import com.zf.easyboot.modules.system.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private PermissionMapper permissionMapper;


    @Resource
    private RoleMenuService roleMenuService;


    @Resource
    private RolePermissionService rolePermissionService;


    @Override
    public PageUtils queryList(Map<String, Object> params) {

        Integer currPage = ConverterConstant.converterInt.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterInt.convert(params.get("size"));
        if (currPage == null) {
            currPage = CommonConstant.DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        }
        Integer startPage = currPage == 0 ? currPage * pageSize : (currPage - 1) * pageSize;

        List<RoleEntity> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);

        List<RoleVo> result = list.stream().map(item -> initRoleInfo(item))
                .collect(Collectors.toList());
        PageUtils page = new PageUtils(result, totalCount, pageSize, currPage);

        return page;

    }


    @Override
    public RoleVo findByRoleIdInfo(Long id) {

        RoleEntity item=Optional.ofNullable(baseMapper.selectOne(new QueryWrapper<RoleEntity>()
                .eq("id",id))).orElse(new RoleEntity());
        item.setId(id);


        return initRoleInfo(item);
    }

    private RoleVo initRoleInfo(RoleEntity item) {
        RoleVo roleVo = new RoleVo();
        BeanCopierUtils.copyProperties(item, roleVo);

        String roleIds = ConverterConstant.converterStr.convert(roleVo.getId());
        List<MenuEntity> menuList = menuMapper.findByRoleMenuTree(roleIds);
        roleVo.setMenuList(menuList);

        List<PermissionEntity> permissionList = permissionMapper.findByRolesIdPermission(roleIds);
        roleVo.setPermissionList(permissionList);

        return roleVo;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 保存权限菜单
     *
     * @param roleMenuVo
     */
    @Override
    @Transactional
    public void saveRoleInfo(RoleMenuVo roleMenuVo) {

        Integer type = Optional.ofNullable(roleMenuVo.getTypeId()).orElse(CommonConstant.DELETED);

        Long roleId = roleMenuVo.getRoleId();
        if (roleId == null) {
            return;
        }
        List<Long> menus = roleMenuVo.getMenus();
        List<Long> permissions = roleMenuVo.getPermissions();
        switch (type) {
            case 1:
                //删除对应菜单权限
                roleMenuService.remove(
                        new QueryWrapper<RoleMenuEntity>().eq("role_id", roleId));

                if (!CollectionUtils.isEmpty(menus)) {
                    List<RoleMenuEntity> list =
                            menus.stream().map(item -> buildRoleMenu(item, roleId))
                                    .collect(Collectors.toList());

                    roleMenuService.saveBatch(list);
                }
                break;
            case 2:
                // 保存操作权限
                rolePermissionService.remove(new QueryWrapper<RolePermissionEntity>()
                        .eq("role_id", roleId));
                if (!CollectionUtils.isEmpty(permissions)) {
                    List<RolePermissionEntity> list =
                            permissions.stream().map(item -> buildRolePermission(item, roleId))
                                    .collect(Collectors.toList());

                    rolePermissionService.saveBatch(list);
                }
                break;
            default:
                log.info("权限未知类型:{}", JSON.toJSONString(roleMenuVo));
                break;
        }
    }


    private RolePermissionEntity buildRolePermission(Long id, Long roleId) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPerId(id);
        rolePermissionEntity.setRoleId(roleId);

        return rolePermissionEntity;
    }

    private RoleMenuEntity buildRoleMenu(Long menuId, Long roleId) {
        RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
        roleMenuEntity.setRoleId(roleId);
        roleMenuEntity.setMenuId(menuId);
        return roleMenuEntity;
    }


}
