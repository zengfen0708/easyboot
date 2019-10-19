package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.modules.system.entity.RoleMenuEntity;
import com.zf.easyboot.modules.system.mapper.RoleMenuMapper;
import com.zf.easyboot.modules.system.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/17.
 */
@Slf4j
@Service("roleMenuService")
public class RoleMenuServiceImpl  extends ServiceImpl<RoleMenuMapper, RoleMenuEntity> implements RoleMenuService {
}
