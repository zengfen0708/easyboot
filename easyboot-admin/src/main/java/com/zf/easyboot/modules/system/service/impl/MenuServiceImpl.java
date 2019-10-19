package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.MenuEntity;
import com.zf.easyboot.modules.system.entity.RoleMenuEntity;
import com.zf.easyboot.modules.system.mapper.MenuMapper;
import com.zf.easyboot.modules.system.mapper.RoleMenuMapper;
import com.zf.easyboot.modules.system.service.MenuService;
import com.zf.easyboot.modules.system.vo.MenuTreeVo;
import com.zf.easyboot.modules.system.vo.MenuVo;
import com.zf.easyboot.modules.system.vo.MetaVo;
import com.zf.easyboot.modules.system.vo.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单实现类
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-09-30 11:20:49
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {


    @Resource
    private RoleMenuMapper roleMenuMapper;


    /**
     * 根据权限初始化菜单
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<MenuVo> initMenuTree(String roleIds) {
        //先查询用户对应的权限菜单
        if (StringUtils.isBlank(roleIds)) {
            //返回一个空查询
            return Lists.newArrayList();
        }
        List<MenuEntity> allMenuTree = baseMapper.findByRoleMenuTree(roleIds);

        //判断是否存在配置的菜单
        if (CollectionUtils.isEmpty(allMenuTree)) {
            return Lists.newArrayList();
        }

        List<MenuVo> menuList = allMenuTree.stream()
                .filter(item -> Objects.equals(item.getParentId(), CommonConstant.DEFAULT_PARENTID))
                .map(menuVo -> conversionMenu(menuVo, allMenuTree))
                .collect(Collectors.toList());
        return menuList;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    public MenuVo conversionMenu(MenuEntity menuEntity, List<MenuEntity> menuEntityList) {
        MenuVo menuVo = initMenuInfo(menuEntity);

        List<MenuVo> children = menuEntityList.stream()
                .filter(item ->Objects.equals(item.getParentId(),menuEntity.getId()))
                .map(subPermission -> conversionMenu(subPermission, menuEntityList))
                .collect(Collectors.toList());
        menuVo.setChildrens(children);
        if (Objects.equals(menuVo.getParentId(), CommonConstant.DEFAULT_PARENTID)) {
            menuVo.setPath("/" + menuVo.getPath());
            if (!CollectionUtils.isEmpty(children)) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
            }
        }

        return menuVo;
    }

    @Override
    public PageUtils queryAllMenu(Map<String, Object> params) {
        List<MenuEntity> allMenuTree = baseMapper.findAllMenuTree(params);

        //获取首页
        List<MenuTreeVo> menuTreeList = allMenuTree.stream()
                .filter(item -> Objects.equals(item.getParentId(), CommonConstant.DEFAULT_PARENTID))
                .map(menuItem -> conversionMenuList(menuItem, allMenuTree))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(menuTreeList) && !CollectionUtils.isEmpty(allMenuTree)){
            menuTreeList= allMenuTree.stream().map(menuItem -> conversionMenuList(menuItem, allMenuTree))
                    .collect(Collectors.toList());
        }

        Integer currPage = CommonConstant.DEFAULT_PAGE;
        Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        PageUtils page = new PageUtils(menuTreeList, allMenuTree.size(), pageSize, currPage);

        return page;
    }

    private MenuTreeVo conversionMenuList(MenuEntity menuItem, List<MenuEntity> allMenuTree) {
        MenuTreeVo menuTreeVo = treeMenu(menuItem);

        List<MenuTreeVo> childrenList = allMenuTree.stream().
                filter(allItem -> Objects.equals(allItem.getParentId(),menuItem.getId()))
                .map(item -> conversionMenuList(item, allMenuTree))
                .collect(Collectors.toList());
        menuTreeVo.setChildrens(childrenList);

        return menuTreeVo;
    }


    @Override
    public List<TreeVo> getMenuTree() {
        List<MenuEntity> allMenuTree = baseMapper.findAllMenuTree(Maps.newHashMap());

        List<TreeVo> treeList = allMenuTree.stream()
                .filter(item -> Objects.equals(item.getParentId(),CommonConstant.DEFAULT_PARENTID))
                .map(item -> converSonMenu(item, allMenuTree))
                .collect(Collectors.toList());

        return treeList;
    }

    /**
     * 递归获取下级菜单
     *
     * @param menuEntity
     * @param allMenuTree
     * @return
     */
    private TreeVo converSonMenu(MenuEntity menuEntity, List<MenuEntity> allMenuTree) {
        TreeVo treeVo = initTree(menuEntity);

        List<TreeVo> childrenList = allMenuTree.stream().
                filter(allItem -> Objects.equals(allItem.getParentId(),menuEntity.getId()))
                .map(item -> converSonMenu(item, allMenuTree))
                .collect(Collectors.toList());

        treeVo.setChildren(childrenList);

        return treeVo;
    }

    private TreeVo initTree(MenuEntity item) {
        TreeVo treeVo = new TreeVo();
        treeVo.setId(item.getId());
        treeVo.setLabel(item.getMenuName());

        return treeVo;
    }

    @Override
    @Transactional
    public ApiMessage saveMenuInfo(MenuEntity menuEntity) {

        baseMapper.insert(menuEntity);
        //新增的菜单,添加管理员权限

        Long menuId = menuEntity.getId();

        RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
        roleMenuEntity.setRoleId(1L);
        roleMenuEntity.setMenuId(menuId);

        roleMenuMapper.insert(roleMenuEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 初始化菜单目录
     *
     * @param menuEntity
     * @return
     */
    private MenuTreeVo treeMenu(MenuEntity menuEntity) {
        MenuTreeVo menuTreeVo = new MenuTreeVo();

        BeanCopierUtils.copyProperties(menuEntity, menuTreeVo);
        menuTreeVo.setName(menuEntity.getMenuName());

        menuTreeVo.setChildrens(Lists.newArrayList());
        return menuTreeVo;
    }


    /**
     * 将菜单转换为对应的前端Tree Node属性
     *
     * @param menuEntity
     * @return
     */
    private MenuVo initMenuInfo(MenuEntity menuEntity) {
        MenuVo menuVo = new MenuVo();


        menuVo.setComponent(StringUtils.isBlank(menuEntity.getComponent()) ?
                "Layout" :
                menuEntity.getComponent());
        Long id = menuEntity.getId();
        menuVo.setName(menuEntity.getMenuName() + id);
        String path = menuEntity.getPath();

        menuVo.setId(id);
        menuVo.setParentId(menuEntity.getParentId());
        if (StringUtils.isBlank(path)) {
            menuVo.setPath(ConverterConstant.converterStr.convert(id));
        } else {
            menuVo.setPath(path);
        }
        menuVo.setCreateTime(menuEntity.getCreateTime());

        //设置meta属性
        menuVo.setMeta(MetaVo.builder()
                .icon(menuEntity.getIcon())
                .title(menuEntity.getMenuName()).build());

        return menuVo;
    }
}
