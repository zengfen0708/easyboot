package com.zf.easyboot.security.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;
import com.zf.easyboot.common.utils.ApiMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response 通用工具类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Slf4j
public class ResponseUtil {


    /**
     * 往 response 写出 json
     *
     * @param response   响应
     * @param httpStatus 状态
     * @param data       返回数据
     */
    public static void renderJson(HttpServletResponse response, HttpStatus httpStatus, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding(CommonConstant.UTF_8);


            response.getWriter()
                    .write(JSON.toJSONString(ApiMessage.putData(httpStatus, data),
                            false));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }

    /**
     * 往 response exception 写出 json
     *
     * @param response  响应
     * @param exception 异常
     */
    public static void renderJson(HttpServletResponse response, BaseException exception) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding(CommonConstant.UTF_8);
            response.setStatus(HttpServletResponse.SC_OK);


            response.getWriter()
                    .write(JSON.toJSONString(ApiMessage.ofException(exception), false));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }
}
