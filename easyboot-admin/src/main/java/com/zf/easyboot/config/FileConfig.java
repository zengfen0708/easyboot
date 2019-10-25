package com.zf.easyboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置类
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
@Component
@ConfigurationProperties(prefix = "other")
@Data
public class FileConfig {

    //上传路径
    private String uploadPath;

    // 服务器下载文件路径
    private String downPath;
}
