package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/14.
 */
@Data
public class DictDetailSearchVo implements Serializable {

    @ApiModelProperty(value = "字典标识")
    private String dictName;

    @ApiModelProperty(value = "字典描述")
    private Long dictId;

    @ApiModelProperty(value = "字典模糊搜索的值")
    private String label;


}
