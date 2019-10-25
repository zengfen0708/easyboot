package com.zf.easyboot.security.config;

import com.zf.easyboot.security.jwt.JwtAuthenticationFilter;
import com.zf.easyboot.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security 配置
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@EnableWebSecurity //开启 Spring Security 注解
@Order(-1)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)//开启security方法注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomConfig customConfig;


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @Autowired
    private AccessDeniedHandler accessDeniedHandler;


    /**
     * 自定义基于Jwt的安全过滤器
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 构建自定义认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //AuthenticationManager 认证
        auth.userDetailsService(jwtUserDetailsService) //获取认证信息数据
                .passwordEncoder(passwordEncoderBean()); //定义认证方式
    }


    /**
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 关闭 CSRF （默认开启)
                .csrf().disable()
                // 基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面
                .formLogin().disable()
                //配置 Http Basic 验证
                .httpBasic().disable()
                // 认证请求
                .authorizeRequests()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 登出行为由自己实现，
                .and().logout().disable()
                // Session 管理 因为使用了JWT，所以这里不管理Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 授权异常
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)

                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable();

        // 禁用缓存
        http.headers().cacheControl();
        // 添加自定义 JWT 过滤器(在过滤器之前添加 filter)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code http.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity and = web.ignoring().and();

        // 忽略 GET
        customConfig.getIgnoreConfig().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        customConfig.getIgnoreConfig().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnoreConfig().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnoreConfig().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnoreConfig().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnoreConfig().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
         customConfig.getIgnoreConfig().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnoreConfig().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));


        // 按照请求格式忽略
        customConfig.getIgnoreConfig().getPattern().forEach(url -> and.ignoring().antMatchers(url));


    }


}
