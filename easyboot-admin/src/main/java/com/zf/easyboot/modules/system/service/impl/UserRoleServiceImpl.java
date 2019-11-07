package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.modules.system.entity.UserRoleEntity;
import com.zf.easyboot.modules.system.mapper.UserRoleMapper;
import com.zf.easyboot.modules.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("UserRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService  {


    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        baseMapper.deleteByUserId(userId);
    }
}
