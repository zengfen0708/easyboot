package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
@Data
public class JobSearchVo extends PageInfoVo {

    @ApiModelProperty(value = "岗位名称")
    private String jobName;


    @ApiModelProperty(value = "岗位状态")
    private Integer status;
}
