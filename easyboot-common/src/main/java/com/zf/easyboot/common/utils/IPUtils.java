package com.zf.easyboot.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取ip工具类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/17.
 */
@Slf4j
public class IPUtils {
    public static final String UN_KNOWN = "unknown";

    /**
     * 获取客户端请求ip信息
     *
     * @param request
     * @return
     */
    public static String getIpHost(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (!(StringUtils.isBlank(ip) || "null".equalsIgnoreCase(ip.trim()))
                    && ip.contains(",")) {
                ip = ip.split(",")[0];
            }

        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

    }
}
