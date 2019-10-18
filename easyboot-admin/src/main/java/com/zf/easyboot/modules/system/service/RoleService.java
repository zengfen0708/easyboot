package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.RoleEntity;
import com.zf.easyboot.modules.system.vo.RoleMenuVo;
import com.zf.easyboot.modules.system.vo.RoleVo;

import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
public interface RoleService extends IService<RoleEntity> {


    PageUtils queryList(Map<String,Object> params);

    void deleteById(Long id);

    void saveRoleInfo(RoleMenuVo roleMenuVo);

    RoleVo findByRoleIdInfo(Long id);
}