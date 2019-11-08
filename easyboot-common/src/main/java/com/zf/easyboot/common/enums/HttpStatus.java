package com.zf.easyboot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 设置前端返回状态码
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/18.
 */
@Getter
@AllArgsConstructor
@ToString
public enum HttpStatus {

    SUCCESS(0, "success"),
    /**
     * 操作异常！
     */
    ERROR(500, "操作异常！"),

    /**
     * 退出成功！
     */
    LOGOUT(0, "退出成功！"),

    /**
     * 请先登录！
     */
    UNAUTHORIZED(401, "请先登录！"),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(403, "权限不足！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(404, "请求不存在！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD(405, "请求方式不支持！"),

    /**
     * 请求异常！
     */
    BAD_REQUEST(400, "请求异常！"),

    /**
     * 参数不匹配！
     */
    PARAM_NOT_MATCH(400, "参数不匹配！"),

    /**
     * 参数不能为空！
     */
    PARAM_NOT_NULL(400, "参数不能为空！"),

    /**
     * 当前用户已被锁定，请联系管理员解锁！
     */
    USER_DISABLED(403, "当前用户已被锁定，请联系管理员解锁！"),

    PAGE_ERROR(10000,"分页参数异常,请核实"),


    /**
     * 用户名或密码错误！
     */
    USERNAME_PASSWORD_ERROR(10001, "用户名或密码错误！"),

    /**
     * token 已过期，请重新登录！
     */
    TOKEN_EXPIRED(10002, "token 已过期，请重新登录！"),

    /**
     * token 解析失败，请尝试重新登录！
     */
    TOKEN_PARSE_ERROR(10002, "token 解析失败，请尝试重新登录！"),
    /**
     * 当前用户已在别处登录，请尝试更改密码或重新登录！
     */
    TOKEN_OUT_OF_CTRL(10003, "当前用户已在别处登录，请尝试更改密码或重新登录！"),
    /**
     * 验证码不能为空！
     */
    REQUEST_NOT_CODE(10004, "验证码不能为空"),
    CODE_ERROR(10005, "验证码过期或者输入不正确"),
    ACCOUNT_ERROR(10006, "账号已停用,请联系管理员"),

    /**
     * 文件上传失败
     */
    FILE_ERROR(10007, "文件上传失败"),
    FILE_IMPORT_ERROR(10008,"上传的excel数据为空,请核实");


    /**
     * code编码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String message;

}
