package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.modules.system.entity.RolePermissionEntity;
import com.zf.easyboot.modules.system.mapper.RolePermissionMapper;
import com.zf.easyboot.modules.system.service.RolePermissionService;
import org.springframework.stereotype.Service;


@Slf4j
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends
        ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {


}
