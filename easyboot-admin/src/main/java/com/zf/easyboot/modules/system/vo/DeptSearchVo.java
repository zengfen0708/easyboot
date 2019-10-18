package com.zf.easyboot.modules.system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/14.
 */
@Data
public class DeptSearchVo extends PageInfoVo {

    @ApiModelProperty(value = "搜索部门名称")
    @JSONField(format="trim")
    private String name;

    @ApiModelProperty(value = "部门状态")
    private Integer status;

}
