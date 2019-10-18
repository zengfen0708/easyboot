package com.zf.easyboot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/22.
 */
@Getter
@AllArgsConstructor
public enum DateUnit {

    Millis("毫秒"),
    SECOND("秒"),
    MINUTE("分钟"),
    HOUR("小时"),
    DAY("天");

    private String desc;
}
