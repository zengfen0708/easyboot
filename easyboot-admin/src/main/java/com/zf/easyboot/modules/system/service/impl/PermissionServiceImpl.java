package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import com.zf.easyboot.modules.system.mapper.PermissionMapper;
import com.zf.easyboot.modules.system.service.PermissionService;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;
import com.zf.easyboot.modules.system.vo.TreeNodeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {


    /**
     * 初始化列表加载树结构
     *
     * @param params
     * @return
     */
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

        List<PermissionEntity> list = Optional.ofNullable(baseMapper.queryAllList(params)).orElse(Lists.newArrayList());
        Integer totalCount = list.size();


        List<TreeNodeVo> result = list.stream()
                .filter(item -> Objects.equals(item.getParentId(), CommonConstant.DEFAULT_PARENTID))
                .map(item -> converTreeNode(item, list))
                .collect(Collectors.toList());

        //不存在父节点,并且查询的结果不为空
        if (!CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(result)) {
            result = list.parallelStream().map(item -> initTreeInfo(item))
                    .collect(Collectors.toList());
        }


        PageUtils page = new PageUtils(result, totalCount, pageSize, currPage);


        return page;
    }

    private TreeNodeVo converTreeNode(PermissionEntity permissionEntity, List<PermissionEntity> list) {
        TreeNodeVo treeNodeVo = initTreeInfo(permissionEntity);

        List<TreeNodeVo> childrenList = list.stream().
                filter(allItem -> Objects.equals(allItem.getParentId(), permissionEntity.getId()))
                .map(item -> converTreeNode(item, list))
                .collect(Collectors.toList());

        treeNodeVo.setChildrens(childrenList);

        return treeNodeVo;
    }

    /**
     * 添加中初始化树形菜单结构
     *
     * @param params
     * @return
     */
    @Override
    public List<TreeNodeAddVo> treeBulid(Map<String, Object> params) {
        List<PermissionEntity> list = Optional.ofNullable(baseMapper.queryAllList(params)).orElse(Lists.newArrayList());

        List<TreeNodeAddVo> result = list.stream()
                .filter(item -> Objects.equals(item.getParentId(), CommonConstant.DEFAULT_PARENTID))
                .map(item -> converTreeNodeAdd(item, list))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(result)) {
            result = list.parallelStream().map(item -> initTreeInfoNode(item))
                    .collect(Collectors.toList());
        }


        return result;
    }

    private TreeNodeAddVo converTreeNodeAdd(PermissionEntity permissionEntity, List<PermissionEntity> list) {
        TreeNodeAddVo treeNodeAddVo = initTreeInfoNode(permissionEntity);

        List<TreeNodeAddVo> childrenList = list.stream().
                filter(allItem -> Objects.equals(allItem.getParentId(), permissionEntity.getId())).
                map(item -> converTreeNodeAdd(item, list))
                .collect(Collectors.toList());

        treeNodeAddVo.setChildren(childrenList);

        return treeNodeAddVo;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        List<Long> list = baseMapper.queryByPermissionIds(id);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String ids = Joiner.on(",").join(list);
        int count = baseMapper.updateSonPermissionId(ids);
        log.info("返回修改行数:{}", count);

    }


    private TreeNodeVo initTreeInfo(PermissionEntity item) {
        TreeNodeVo treeNodeVo = new TreeNodeVo();
        BeanCopierUtils.copyProperties(item, treeNodeVo);
        return treeNodeVo;
    }


    private TreeNodeAddVo initTreeInfoNode(PermissionEntity permissionEntity) {
        TreeNodeAddVo deptVo = new TreeNodeAddVo();
        BeanCopierUtils.copyProperties(permissionEntity, deptVo);
        return deptVo;
    }
}
