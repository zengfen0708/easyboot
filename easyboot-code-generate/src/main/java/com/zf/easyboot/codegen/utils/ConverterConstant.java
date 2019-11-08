package com.zf.easyboot.codegen.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
public class ConverterConstant {
    @FunctionalInterface
    public static interface Converter<F, T> {
        T convert(F from);
    }

    //转换为object为String 的方法
    public static Converter<String, String> converterStr = (from) -> {
        if (StringUtils.isEmpty(from)) {
            return null;
        }
        return String.valueOf(from);
    };
}
