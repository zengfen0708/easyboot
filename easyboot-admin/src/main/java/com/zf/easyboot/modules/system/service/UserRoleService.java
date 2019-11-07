package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.modules.system.entity.UserRoleEntity;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    /**
     * 根据用户id删除角色
     */
    void deleteByUserId( Long userId);
}
