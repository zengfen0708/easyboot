package com.zf.easyboot.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.zf.easyboot.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private Long id;

    private String username; //登录名

    private String password;

    private String  nickname;

    private Integer state;

    private String roleIds;

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未被锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    //是否启用
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    //设置对应权限信息
    public Collection getRoles() {

        Set<String> roles=authorities.stream().map(item->{
                return  StringUtils.replace(((GrantedAuthority) item).getAuthority(),
                "ROLE_","");})
                .collect(Collectors.toSet());

        return roles;
    }
}
