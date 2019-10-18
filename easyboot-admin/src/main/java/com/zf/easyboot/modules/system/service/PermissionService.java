package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;

import java.util.List;
import java.util.Map;


/**
 * 权限
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface PermissionService extends IService<PermissionEntity> {


    PageUtils queryList(Map<String,Object> params);

    List<TreeNodeAddVo> treeBulid(Map<String,Object> params);

    void deleteById(Long id);
}

