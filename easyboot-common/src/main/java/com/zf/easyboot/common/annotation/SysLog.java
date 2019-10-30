package com.zf.easyboot.common.annotation;

import java.lang.annotation.*;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 备注
     *
     * @return
     */
    String value() default "";

    /**
     * 系统来源
     *
     * @return
     */
    int source() default 0;
}
