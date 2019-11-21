package com.zf.easyboot.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * 序列化反序列化工具
 * @author zhangwei
 * @date 2019.9.20
 */
public class SerializeUtil {
    private static Logger logger = LogManager.getLogger(SerializeUtil.class);


    public static byte[] serialize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos= null;

        try {
            //序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);

            oos.writeObject(object);
            byte[] byteArray = bos.toByteArray();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            free(oos);
            free(bos);
        }
        return null;
    }


    public static Object unSerialize(byte[] bytes){
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //反序列化
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            free(ois);
            free(bis);
        }
        return null;
    }



    /**
     * 释放资源
     * @param close
     */
    private static void free(Closeable close){
        try {
            if(close != null){
                close.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
