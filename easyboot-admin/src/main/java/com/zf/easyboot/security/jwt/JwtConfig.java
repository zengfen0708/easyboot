package com.zf.easyboot.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Data
@ConfigurationProperties(prefix = "jwt.config")
@Component
public class JwtConfig {

    /**
     * jwt 加密 key，默认值：zfcoding.
     */
    private String key="zfcoding";



    /**
     * jwt 过期时间，默认值：86400L {@code 1 天}.
     */
    private Long expiration = 86400L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800 {@code 7 天}
     */
    private Long remember = 604800L;

    /**
     *  JWT 在 Redis 中保存的key前缀
     */
    private String  jwtRedisKey="zfsecurity:simpleboot:jwt:";

    /**
     * 请求头部信息
     */
    private String header="ZFSimple-Auth";

}
