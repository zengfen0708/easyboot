package com.zf.easyboot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/21.
 */
@Getter
@AllArgsConstructor
@ToString
public enum LogTypeEnum {

    SUSSESS(0, "请求成功"),
    ERROR(1, "异常日志");

    private Integer code;
    private String name;
}
