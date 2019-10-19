package com.zf.easyboot.modules.system.vo;

import com.zf.easyboot.modules.system.entity.JobEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
@Data
public class JobVo extends JobEntity {

    @ApiModelProperty(value = "部门信息")
    private String deptName;

    @ApiModelProperty(value = "部门父类id")
    private Long deptParentId;

}
