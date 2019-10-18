package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;

import java.util.List;
import java.util.Map;


/**
 * 部门表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DeptService extends IService<DeptEntity> {


    PageUtils queryList(Map<String,Object> params);

    List<TreeNodeAddVo> getDeptTree(Map<String,Object> params);

    Integer deleteById(Long id);

    /**
     * 获取部门结构
     * @return
     */
    Map<Long,String> initDeptMapInfo();
}

