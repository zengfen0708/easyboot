package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
@Data
public class RoleSearchVo extends PageInfoVo {

    @ApiModelProperty(value = "搜索角色名称")
    private String roleName;
}
