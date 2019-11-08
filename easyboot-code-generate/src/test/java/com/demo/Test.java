package com.demo;

import cn.hutool.core.text.UnicodeUtil;
import com.zf.easyboot.codegen.utils.ConverterConstant;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.Optional;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(UnicodeUtil.toUnicode("疯信子"));

        System.out.println(UnicodeUtil.toString("\\u7C7B\\u578B\\u8F6C\\u6362\\uFF0C\\u914D\\u7F6E\\u4FE1\\u606F"));
    }
}