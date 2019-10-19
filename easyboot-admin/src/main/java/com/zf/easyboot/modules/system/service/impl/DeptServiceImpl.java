package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import com.zf.easyboot.modules.system.mapper.DeptMapper;
import com.zf.easyboot.modules.system.service.DeptService;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;
import com.zf.easyboot.modules.system.vo.TreeNodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptEntity> implements DeptService {


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
        List<DeptEntity> list = Optional.ofNullable(baseMapper.queryList(params))
                .orElse(Lists.newArrayList());
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

    /**
     * 递归获取下级元素节点
     *
     * @param deptEntity
     * @param list
     * @return
     */
    private TreeNodeVo converTreeNode(DeptEntity deptEntity, List<DeptEntity> list) {
        TreeNodeVo treeNodeVo = initTreeInfo(deptEntity);

        List<TreeNodeVo> treeList = list.stream()
                .filter(item -> Objects.equals(item.getParentId(),deptEntity.getId()))
                .map(item -> converTreeNode(item, list))
                .collect(Collectors.toList());

        treeNodeVo.setChildrens(treeList);

        return treeNodeVo;

    }


    /**
     * 初始化树形菜单
     *
     * @param params
     * @return
     */
    @Override
    public List<TreeNodeAddVo> getDeptTree(Map<String, Object> params) {

        List<DeptEntity> list = Optional.ofNullable(baseMapper.queryList(params)).orElse(Lists.newArrayList());


        List<TreeNodeAddVo> result = list.stream()
                .filter(item -> Objects.equals(item.getParentId(), CommonConstant.DEFAULT_PARENTID))
                .map(item -> converTreeNodeAdd(item, list))
                .collect(Collectors.toList());


        //不存在父节点,并且查询的结果不为空
        if (!CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(result)) {
            result = list.parallelStream().map(item -> initTreeInfoNode(item))
                    .collect(Collectors.toList());
        }

        return result;
    }

    private TreeNodeAddVo converTreeNodeAdd(DeptEntity deptEntity, List<DeptEntity> list) {
        TreeNodeAddVo treeNodeAddVo = initTreeInfoNode(deptEntity);

        List<TreeNodeAddVo> treeList = list.stream()
                .filter(item -> Objects.equals(item.getParentId(), deptEntity.getId()))
                .map(item -> converTreeNodeAdd(item, list))
                .collect(Collectors.toList());

        treeNodeAddVo.setChildren(treeList);
        return treeNodeAddVo;
    }

    /**
     * 删除部门数据
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Long id) {
        List<Long> list = baseMapper.queryByDeptIds(id);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        String ids = Joiner.on(",").join(list);
        //暂时只支持三级，如果超过三级的需要递归去查询id信息
        List<Long> sonList = baseMapper.querySonDeptIds(ids);
        String sonIds = Joiner.on(",").join(sonList);
        int count = baseMapper.updateDeptId(sonIds);
        log.info("返回修改行数:{}", count);

        return count;
    }

    /**
     * 初始化返回所有的部门结构
     *
     * @return
     */
    @Override
    public Map<Long, String> initDeptMapInfo() {
        List<DeptEntity> list = Optional.ofNullable(baseMapper.selectList(new QueryWrapper<DeptEntity>())).orElse(Lists.newArrayList());
        Map<Long, String> map = list.parallelStream()
                .collect(Collectors.toMap(DeptEntity::getId, DeptEntity::getName));
        map = Optional.ofNullable(map).orElse(Maps.newHashMap());
        return map;
    }


    private TreeNodeVo initTreeInfo(DeptEntity deptEntity) {
        TreeNodeVo treeNodeVo = new TreeNodeVo();
        BeanCopierUtils.copyProperties(deptEntity, treeNodeVo);
        return treeNodeVo;
    }


    private TreeNodeAddVo initTreeInfoNode(DeptEntity deptEntity) {
        TreeNodeAddVo deptVo = new TreeNodeAddVo();
        BeanCopierUtils.copyProperties(deptEntity, deptVo);
        deptVo.setAlias(deptVo.getName());
        return deptVo;
    }
}
