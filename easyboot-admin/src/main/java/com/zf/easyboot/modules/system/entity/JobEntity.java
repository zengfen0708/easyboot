package com.zf.easyboot.modules.system.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 岗位表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_job")
@ApiModel(value = "权限数据", description = "保存权限数据")
public class JobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String name;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sort;
    /**
     * 状态，正常-1，停用-0
     */
    @ApiModelProperty(value = "状态，正常-1，停用-0")
    private Integer status;
    /**
     * 软删除标识(0 有效 1 已删除)
     */
    @ApiModelProperty(value = "软删除标识(0 有效 1 已删除)")
    private Integer deleted;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}

