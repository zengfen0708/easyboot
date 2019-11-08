package com.zf.easyboot.codegen.controller;

import com.zf.easyboot.codegen.common.ApiMessage;
import com.zf.easyboot.codegen.common.EasybootConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Controller
@Slf4j
public class IndexController {

    @Resource
    private EasybootConfig easybootConfig;

    @RequestMapping("/")
    public  String index(){
        return  "index";
    }


    /**
     * 初始化信息
     * @return
     */
    @RequestMapping(value = "init",method = RequestMethod.GET)
    @ResponseBody
    public ApiMessage  init(){


        return  ApiMessage.success(easybootConfig);
    }
}
