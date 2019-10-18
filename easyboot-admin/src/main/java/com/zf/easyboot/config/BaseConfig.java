package com.zf.easyboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础配置项目类
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/12.
 */
@Component
@ConfigurationProperties(prefix = "base")
@Data
public class BaseConfig {


    private List<String> corsList;

}
