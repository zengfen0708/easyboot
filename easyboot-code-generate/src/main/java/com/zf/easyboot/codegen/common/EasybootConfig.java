package com.zf.easyboot.codegen.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 初始化信息
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Component
@ConfigurationProperties(prefix = "easyboot.mysql")
@Data
public class EasybootConfig {

    /**
     * 连接字符串
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
