package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Data
@ApiModel(value = "查询用户信息")
public class UserSearchVo extends PageInfoVo {


    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "查询类型")
    private String type;
    @ApiModelProperty(value = "状态(启用-1，禁用-0) ")
    private Integer enabled;
}
