package com.zf.easyboot.modules.system.entity;

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

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表详情表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_detail")
@ApiModel(value = "字典表详情表数据", description = "保存字典表详情表数据")
public class DictDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签")
    private String label;
    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private String value;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sort;
    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private Long dictId;
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
