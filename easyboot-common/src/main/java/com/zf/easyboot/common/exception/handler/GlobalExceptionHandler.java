package com.zf.easyboot.common.exception.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;
import com.zf.easyboot.common.exception.SecurityException;
import com.zf.easyboot.common.utils.ApiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 全局统一异常处理
 * (暂时还没有想到好方法去优化那么多if-else)
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiMessage handlerException(Exception e) {


        if (e instanceof NoHandlerFoundException) {
            NoHandlerFoundException error = (NoHandlerFoundException) e;
            log.error("[全局异常拦截】NoHandlerFoundException: 请求方法 {}, 请求路径 {}",
                    error.getRequestURL(), error.getHttpMethod());

            return ApiMessage.ofSuccess(HttpStatus.REQUEST_NOT_FOUND);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {

            HttpRequestMethodNotSupportedException error = (HttpRequestMethodNotSupportedException) e;
            log.error("【全局异常拦截】HttpRequestMethodNotSupportedException: " +
                            "当前请求方式 {}, 支持请求方式 {}",
                    error.getMethod(),
                    JSONUtil.toJsonStr(error.getSupportedHttpMethods()));

            return ApiMessage.putHttpStatus(HttpStatus.HTTP_BAD_METHOD);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("【全局异常拦截】MethodArgumentNotValidException", e);
            MethodArgumentNotValidException error = (MethodArgumentNotValidException) e;

            return ApiMessage.of(HttpStatus.BAD_REQUEST.getCode(),
                    error.getBindingResult()
                            .getAllErrors()
                            .get(0)
                            .getDefaultMessage(), null);

        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException error = (ConstraintViolationException) e;
            log.error("【全局异常拦截】ConstraintViolationException", e);
            return ApiMessage.of(HttpStatus.BAD_REQUEST.getCode(),
                    CollUtil.getFirst(error
                            .getConstraintViolations())
                            .getMessage(), null);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException error = (MethodArgumentTypeMismatchException) e;

            log.error("【全局异常拦截】MethodArgumentTypeMismatchException:" +
                            " 参数名 {}, 异常信息 {}", error.getName(),
                    error.getMessage());
            return ApiMessage.putHttpStatus(HttpStatus.PARAM_NOT_MATCH);
        } else if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException error = (HttpMessageNotReadableException) e;
            log.error("【全局异常拦截】HttpMessageNotReadableException: " +
                    "错误信息 {}", error.getMessage());
            return ApiMessage.putHttpStatus(HttpStatus.PARAM_NOT_NULL);
        } else if (e instanceof BadCredentialsException) {

            log.error("【全局异常拦截】BadCredentialsException: 错误信息 {}", e.getMessage());
            return ApiMessage.putHttpStatus(HttpStatus.USERNAME_PASSWORD_ERROR);
        } else if (e instanceof DisabledException) {
            log.error("【全局异常拦截】BadCredentialsException: 错误信息 {}", e.getMessage());
            return ApiMessage.putHttpStatus(HttpStatus.USER_DISABLED);
        } else if (e instanceof AccessDeniedException) {
            log.error("【全局异常拦截】DataManagerException:  异常信息 {}",
                    e.getMessage());
            return ApiMessage.putHttpStatus(HttpStatus.ACCESS_DENIED);
        } else if (e instanceof BaseException) {
            BaseException error = (BaseException) e;
            log.error("【全局异常拦截】DataManagerException: 状态码 {}, 异常信息 {}",
                    error.getCode(), e.getMessage());
            return ApiMessage.ofException((BaseException) e);
        }else if (e instanceof SecurityException){
            SecurityException error = (SecurityException) e;
            log.error("【全局异常拦截】SecurityException: 状态码 {}, 异常信息 {}",
                    error.getCode(), e.getMessage());
            return ApiMessage.ofException((BaseException) e);
        }
        log.error("【全局异常拦截】: 异常信息 {} ", e);
        return ApiMessage.putHttpStatus(HttpStatus.ERROR);

    }


}
