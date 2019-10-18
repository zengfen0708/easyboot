package com.zf.easyboot.security.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.security.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security工具类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@UtilityClass
public class SecurityUtil {

    /**
     * 获取当前登录用户用户名
     *
     * @return 当前登录用户用户名
     */
    public  String getCurrentUsername() {
        JwtUser currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ?
                CommonConstant.ANONYMOUS_NAME :
                currentUser.getUsername();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public  JwtUser getCurrentUser() {
        Object userInfo = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (userInfo instanceof UserDetails) {
            return (JwtUser) userInfo;
        }
        return null;
    }

    public SecurityContext getSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.getContext();


        return securityContext;
    }

}
