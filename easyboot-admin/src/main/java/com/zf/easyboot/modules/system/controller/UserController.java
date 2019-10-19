package com.zf.easyboot.modules.system.controller;

import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.service.UserService;
import com.zf.easyboot.modules.system.vo.UserSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户模块
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "用户模块", tags = "用户模块")
@RestController
@RequestMapping("/system/user")
public class UserController {


    @Resource
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("获取全部信息")
    public ApiMessage list(@RequestBody UserSearchVo userSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(userSearchVo);
        PageUtils page = userService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

}
