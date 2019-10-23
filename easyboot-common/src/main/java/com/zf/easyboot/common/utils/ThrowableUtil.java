package com.zf.easyboot.common.utils;

import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/21.
 */
@UtilityClass
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
