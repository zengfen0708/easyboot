package com.zf.easyboot.codegen.common;

import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Getter
public enum ResultCode implements IResultCode {
    /**
     * 成功
     */
    OK(200, "成功"),
    /**
     * 失败
     */
    ERROR(500, "失败");
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
