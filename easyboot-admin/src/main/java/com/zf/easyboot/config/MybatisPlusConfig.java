package com.zf.easyboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置文件
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/12.
 */
@Configuration
@MapperScan(basePackages = {"com.zf.easyboot.modules.*.mapper"})
public class MybatisPlusConfig {
}
