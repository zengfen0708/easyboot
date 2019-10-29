package com.zf.easyboot.common.utils;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Base64位加密解密工具
 * @author zhangwei
 */
public class Base64Util {

    /**
     * 编码
     * @param source
     * @return
     */
    public static String encode(String source){
        return encode(source.getBytes());
    }

    /**
     * 编码
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }



    public static byte[] decode(byte[] bytes){
        return Base64.getDecoder().decode(bytes);
    }


    public static String decode(String source){
        return new String(decode(source.getBytes()));
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("30");
        list.add("address s s s s s");

        String s = "aaa";


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String encode = Base64Util.encode(SerializeUtil.serialize(list));
        System.out.println(encode);


        Object o = SerializeUtil.unSerialize(Base64Util.decode(encode.getBytes()));
        System.out.println(o);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }



}
