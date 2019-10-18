package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/15.
 */
@Data
public class DictSearchVo extends PageInfoVo {

    @ApiModelProperty(value = "输入的值")
    private String value;

    @ApiModelProperty(value = "查询的类型")
    private String type;
}
