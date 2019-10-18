package com.zf.easyboot.security.jwt;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.SecurityException;
import com.zf.easyboot.common.utils.IPUtils;
import com.zf.easyboot.security.config.CustomConfig;
import com.zf.easyboot.security.config.IgnoreConfig;
import com.zf.easyboot.security.service.JwtUserDetailsService;
import com.zf.easyboot.security.utils.ResponseUtil;
import com.zf.easyboot.security.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

/**
 * Jwt 认证过滤器
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;

    /**
     * secure过滤的请求
     */
    @Autowired
    private CustomConfig customConfig;


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取请求头中jwt信息
        String jwt = jwtUtil.resolveToken(request);

        if (StringUtils.isNoneBlank(jwt)) {

            try {
                String username = jwtUtil.getUsernameToJwt(jwt);

                SecurityContext securityContext = SecurityUtil.getSecurityContext();

                if (username != null && securityContext.getAuthentication() == null) {
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

                    //进行authenticate方法进行认证处理
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //将认证信息对象绑定到 SecurityContext
                    securityContext.setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request, response);
            } catch (SecurityException e) {
                ResponseUtil.renderJson(response, e);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("客户请求认证失败\t\n{} 请求接口：{},请求IP：{}\n\t请求参数：{}",
                        request.getMethod(), request.getRequestURI(), IPUtils.getIpHost(request),
                        JSON.toJSON(request.getParameterMap()));
            }
            ResponseUtil.renderJson(response, HttpStatus.UNAUTHORIZED, null);
        }
    }


    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request
     * @return
     */
    private boolean checkIgnores(HttpServletRequest request) {

        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignoresSet = Sets.newHashSet();

        IgnoreConfig ignoreConfig = customConfig.getIgnoreConfig();
        switch (httpMethod) {
            case GET:
                ignoresSet.addAll(ignoreConfig.getGet());
                break;
            case POST:
                ignoresSet.addAll(ignoreConfig.getPost());
                break;
            case PUT:
                ignoresSet.addAll(ignoreConfig.getPut());
                break;
            case HEAD:
                ignoresSet.addAll(ignoreConfig.getHead());
                break;
            case PATCH:
                ignoresSet.addAll(ignoreConfig.getPatch());
                break;
            case TRACE:
                ignoresSet.addAll(ignoreConfig.getTrace());
                break;
            case DELETE:
                ignoresSet.addAll(ignoreConfig.getDelete());
                break;
            case OPTIONS:
                ignoresSet.addAll(ignoreConfig.getOptions());
                break;
            default:
                break;
        }
        ignoresSet.addAll(ignoreConfig.getPattern());

        if (CollUtil.isNotEmpty(ignoresSet)) {
            for (String ignore : ignoresSet) {
                AntPathRequestMatcher mathcer = new AntPathRequestMatcher(ignore, method);
                if (mathcer.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
