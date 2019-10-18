package com.zf.easyboot.security.config;

import com.zf.easyboot.common.enums.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        /**
         * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送403 响应
         */
        HttpStatus httpStatus = HttpStatus.ACCESS_DENIED;
        response.sendError(httpStatus.getCode(), httpStatus.getMessage());

    }
}
