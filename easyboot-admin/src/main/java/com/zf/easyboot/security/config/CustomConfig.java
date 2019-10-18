package com.zf.easyboot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Data
@ConfigurationProperties(prefix ="zfcustom.config" )
@Component
public class CustomConfig {

    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignoreConfig;
}
