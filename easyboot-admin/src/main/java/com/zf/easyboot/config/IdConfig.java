package com.zf.easyboot.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.zf.easyboot.common.utils.ConverterConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 雪花主键生成器
 * 如果是集群会出现重复，单机不会出现重复 19
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Configuration
public class IdConfig {

    @Autowired
    private Snowflake snowflake;

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

    /**
     * 返回雪花算法唯一id
     *
     * @return
     */
    public String onlyId() {

        return ConverterConstant.converterLong.convert(snowflake.nextId());
    }
}
