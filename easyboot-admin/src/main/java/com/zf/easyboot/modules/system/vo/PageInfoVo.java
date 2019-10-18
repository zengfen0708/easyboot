package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Data
public class PageInfoVo implements Serializable {

    @ApiModelProperty(value = "每页显示总数")
    private Integer size;

    @ApiModelProperty(value = "开始页数")
    private Integer page;

}
