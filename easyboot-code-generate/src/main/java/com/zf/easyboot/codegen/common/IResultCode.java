package com.zf.easyboot.codegen.common;

/**
 * 统一状态码接口
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
public interface IResultCode {


    /**
     * 获取状态码
     * @return 状态码
     */
    Integer getCode();


    /**
     * 获取返回消息
     * @return 返回消息
     */
    String getMessage();
}
