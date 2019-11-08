package com.zf.easyboot.codegen.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API对象返回
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Data
@NoArgsConstructor
public class ApiMessage<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 状态
     */
    private boolean status;

    /**
     * 返回数据
     */
    private T data;


    public ApiMessage(Integer code, String message, boolean status, T data) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ApiMessage(ResultCode resultCode, boolean status, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.status = status;
        this.data = data;
    }

    public ApiMessage(ResultCode resultCode, boolean status) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.status = status;
        this.data = null;
    }

    public static <T> ApiMessage success() {
        return new ApiMessage<>(ResultCode.OK, true);
    }

    public static <T> ApiMessage message(String message) {
        return new ApiMessage<>(ResultCode.OK.getCode(), message, true, null);
    }

    public static <T> ApiMessage success(T data) {
        return new ApiMessage<>(ResultCode.OK, true, data);
    }

    public static <T> ApiMessage fail() {
        return new ApiMessage<>(ResultCode.ERROR, false);
    }

    public static <T> ApiMessage fail(ResultCode resultCode) {
        return new ApiMessage<>(resultCode, false);
    }

    public static <T> ApiMessage fail(Integer code, String message) {
        return new ApiMessage<>(code, message, false, null);
    }

    public static <T> ApiMessage fail(ResultCode resultCode, T data) {
        return new ApiMessage<>(resultCode, false, data);
    }

    public static <T> ApiMessage fail(Integer code, String message, T data) {
        return new ApiMessage<>(code, message, false, data);
    }
}
