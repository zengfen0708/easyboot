package com.zf.easyboot.modules.work;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Controller
@Slf4j
@RequestMapping(value = "/hello")
public class HelloController {

    /**
     * 由于默认
     * @param request
     * @return
     */
    @RequestMapping(value = "/index.html",method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        log.info("查询首页信息");

        return "index";
    }


    @RequestMapping(value = "/init",method = RequestMethod.GET)
    @ResponseBody
    public String init(HttpServletRequest request) {
        log.info("查询信息返回");

        return "index";
    }

}
