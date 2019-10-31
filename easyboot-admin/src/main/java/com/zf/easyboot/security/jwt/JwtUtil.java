package com.zf.easyboot.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.SecurityException;
import com.zf.easyboot.config.util.RedisUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Slf4j
@Configuration
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;


    @Resource
    private RedisUtils redisUtils;

    /**
     * 创建JWT
     *
     * @param rememberMe 是否记住我
     * @param id         用户id
     * @param subject    用户名
     * @return JWT
     */
    public String createJwt(Boolean rememberMe, Long id,
                            String subject) {

        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getKey());


        //设置token过期时间
        Long expirationTime = rememberMe
                ? jwtConfig.getRemember() :
                jwtConfig.getExpiration();

        if (expirationTime > 0) {
            builder.setExpiration(new Date(now.getTime() + expirationTime * 1000));
        }

        String jwt = builder.compact();

        // 将生成的JWT保存至Redis(设置超时时间为毫秒)

        redisUtils.set(jwtConfig.getJwtRedisKey() + subject, jwt, expirationTime);

        //重新登录必须删除用户角色有关信息
        String key = jwtConfig.getJwtRedisKey() + "userDetails:" + subject;
        redisUtils.delete(key);

        return jwt;
    }


    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     是否记住我
     * @return jwt
     */
    public String createJwt(Authentication authentication, Boolean rememberMe) {

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        return createJwt(rememberMe,
                jwtUser.getId(),
                jwtUser.getUsername());
    }


    /**
     * 解析jwt
     *
     * @param jwt jwt
     * @return
     */
    public Claims validateJwtToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            String jwtRedisKey = jwtConfig.getJwtRedisKey() + username;
            // 校验redis中的JWT是否存在
            Long expire = redisUtils.getExpire(jwtRedisKey);

            if (Objects.isNull(expire) || expire <= 0) {
                throw new SecurityException(HttpStatus.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，
            // 不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = redisUtils
                    .get(jwtRedisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new SecurityException(HttpStatus.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException(HttpStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException(HttpStatus.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException(HttpStatus.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException(HttpStatus.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException(HttpStatus.TOKEN_PARSE_ERROR);
        }
    }


    /**
     * 设置jwt过期
     *
     * @param request 请求
     */
    public void invalidateJwt(HttpServletRequest request) {

        String jwt = resolveToken(request);

        String username = getUsernameToJwt(jwt);

        //从redis中删除jwt
        redisUtils.delete(jwtConfig.getJwtRedisKey() + username);

        //删除用户权限
        String key = jwtConfig.getJwtRedisKey() + "userDetails:" + username;
        redisUtils.delete(key);


    }


    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt
     * @return 用户名
     */
    public String getUsernameToJwt(String jwt) {
        Claims claims = validateJwtToken(jwt);
        return claims.getSubject();
    }


    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return jwt信息
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
