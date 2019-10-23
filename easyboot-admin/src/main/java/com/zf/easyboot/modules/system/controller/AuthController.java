package com.zf.easyboot.modules.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;
import com.zf.easyboot.common.exception.SecurityException;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.VerifyCodeUtils;
import com.zf.easyboot.config.IdConfig;
import com.zf.easyboot.config.util.RedisUtils;
import com.zf.easyboot.modules.system.vo.LoginRequestVo;
import com.zf.easyboot.security.jwt.JwtUser;
import com.zf.easyboot.security.jwt.JwtUtil;
import com.zf.easyboot.security.service.JwtUserDetailsService;
import com.zf.easyboot.security.utils.ImgResult;
import com.zf.easyboot.security.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * 用户模块
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/21.
 */
@Slf4j
@Api(value = "用户模块", tags = "生成用户登录Token")
@RequestMapping("/auth")
@RestController
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IdConfig idConfig;


    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆获取jwt")
    @SysLog(value = "用户登陆")
    public ApiMessage<String> login(@RequestBody LoginRequestVo loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        String uuid = loginRequest.getUuid();
        String reqCode = loginRequest.getCode();

        String code = Optional.ofNullable(redisUtils.get(uuid)).orElseThrow(() ->
                new BaseException(HttpStatus.REQUEST_NOT_CODE));


        if (!reqCode.equalsIgnoreCase(code)) {
            //如果不相等直接返回给前端
            return ApiMessage.putHttpStatus(HttpStatus.CODE_ERROR);
        }
        //删除验证码
        redisUtils.delete(uuid);

        UsernamePasswordAuthenticationToken
                upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);


        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        //是否保留
        Boolean rememberMe = false;
        String jwt = jwtUtil.createJwt(authentication, rememberMe);
        return ApiMessage.ofSuccess(jwt);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出", notes = "清除jwt token 信息")
    @SysLog(value = "用户退出")
    public ApiMessage logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJwt(request);
        } catch (SecurityException e) {
            throw new SecurityException(HttpStatus.UNAUTHORIZED);
        }
        return ApiMessage.putHttpStatus(HttpStatus.LOGOUT);
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiMessage<JwtUser> getUserInfo() {
        JwtUser jwtUser = (JwtUser) jwtUserDetailsService
                .loadUserByUsername(SecurityUtil.getCurrentUsername());

        log.info("返回信息:{}", JSON.toJSONString(jwtUser));

        return ApiMessage.ofSuccess(jwtUser);
    }


    /**
     * 获取验证码
     */
    @ApiOperation(value = "获取验证码", notes = "生成验证码信息")
    @RequestMapping(value = "/authCode", method = RequestMethod.GET)
    public ImgResult getCode(HttpServletRequest  request) throws IOException {
        String uuid = idConfig.onlyId();

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(CommonConstant.SENDCHARCODE);
        //设置redis超时时间 10分钟
        //这个可能会发生恶意刷
        Long codeExpire = 60 * 10L;
        redisUtils.set(uuid, verifyCode, codeExpire);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            return new ImgResult(Base64.encode(stream.toByteArray()), uuid, CommonConstant.STATUS);
        } finally {
            IoUtil.close(stream);
        }

    }
}
