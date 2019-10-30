package com.zf.easyboot.modules.system.controller;

import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.service.LogService;
import com.zf.easyboot.modules.system.vo.LogSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 记录系统日志信息
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/22.
 */
@Slf4j
@Api(value = "系统日志模块", tags = "查询系统日志模块")
@RequestMapping("/system/log")
@RestController
public class LogController {

    @Autowired
    private LogService logService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','LOG_ALL')")
    @ApiOperation("系统日志模块")
    @SysLog("系统日志查询模块")
    public ApiMessage list(@RequestBody LogSearchVo logSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(logSearchVo);
        PageUtils page = logService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }


    @RequestMapping(value = "/getErrorDetail", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','LOG_ERROR_ALL')")
    @ApiOperation("系统异常日志查询模块")
    @SysLog("系统异常日志查询模块")
    public ApiMessage getErrDetail(@RequestBody LogSearchVo logSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(logSearchVo);

        String message = logService.getErrorDetail(params);

        return ApiMessage.ofSuccess(message);

    }


}
