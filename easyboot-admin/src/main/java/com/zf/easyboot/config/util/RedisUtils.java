package com.zf.easyboot.config.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/27.
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "valueOperations")
    private ValueOperations<String, String> valueOperations;
    @Resource(name = "hashOperations")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name = "listOperations")
    private ListOperations<String, Object> listOperations;
    @Resource(name = "setOperations")
    private SetOperations<String, Object> setOperations;
    @Resource(name = "zSetOperations")
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    /**
     * 普通缓存放入并设置时间
     *
     * @param key    键
     * @param value  值
     * @param expire 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long expire) {

        if (expire > 0) {
            valueOperations.set(key, toJson(value), expire, TimeUnit.SECONDS);
        } else {
            valueOperations.set(key, toJson(value));

        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public ListOperations getListOperations() {
        return listOperations;
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

}
