package com.zf.easyboot.common.utils;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;


/**
 *  也可以使用MapStruct代替
 *  具体那种方法自行研究
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/21.
 */
@Slf4j
public class BeanCopierUtils {

    public static Map<String, BeanCopier> beanCopierMap = Maps.newConcurrentMap();


    /**
     * cp对象赋值
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map =Maps.newHashMap();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = getFieldsDirectly(obj.getClass(),true);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
        return map;
    }


    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
        return obj;
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass 类
     * @param withSuperClassFieds 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFieds) throws SecurityException {

        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtil.append(allFields, declaredFields);
            }
            searchType = withSuperClassFieds ? searchType.getSuperclass() : null;
        }

        return allFields;
    }
}
