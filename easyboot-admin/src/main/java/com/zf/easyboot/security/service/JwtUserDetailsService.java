package com.zf.easyboot.security.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.mapper.PermissionMapper;
import com.zf.easyboot.modules.system.mapper.RoleMapper;
import com.zf.easyboot.modules.system.mapper.UserMapper;
import com.zf.easyboot.modules.system.mapper.UserRoleMapper;
import com.zf.easyboot.security.jwt.JwtUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义UserDetails查询
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Resource
    private UserMapper userMapper;


    @Resource
    private UserRoleMapper userRoleMapper;


    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (log.isDebugEnabled()) {
            log.debug("Authenticating user '{}'",
                    username);
        }

        UserEntity user = userMapper.findByUsername(username)
                .orElseThrow(() -> new
                        UsernameNotFoundException("未找到用户信息 : "
                        + username));

        if (java.util.Objects.equals(user.getStatus(), CommonConstant.DISABLE)) {
            throw new BaseException(HttpStatus.ACCOUNT_ERROR);
        }

        return create(user);
    }


    /**
     * 创建jwt用户信息
     *
     * @param user
     * @return
     */
    private JwtUser create(UserEntity user) {

        //查询用户有哪些角色
        List<Long> roles = userRoleMapper.selectByUserIdToRoles(user.getId());

        String roleIds = "";
        //创建一个默认的角色
        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(roles)) {
            //使用guava合并集合
            roleIds = ConverterConstant.converterListToStr.convert(roles);

            //查询对应的权限菜单
            List<String> permissionsList = permissionMapper.selectByRolesIdToPermission(roleIds);

            if (CollectionUtils.isEmpty(permissionsList)) {
                //如果权限为空，手动添加一个默认登录进入系统的权限
                permissionsList.add("ROLE_SYSTEM_BOOT_DEFAULT");
            }

            //获取所有的权限信息 备注用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authorities = permissionsList.stream()
                    .filter(permission -> StrUtil.isNotBlank(permission))
                    .map(permission -> new SimpleGrantedAuthority("ROLE_" + StringUtils.trim(permission)))
                    .collect(Collectors.toList());
        }
        //创建jwt用户
        return new JwtUser(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getNickname(), user.getStatus(), roleIds, authorities);
    }
}
