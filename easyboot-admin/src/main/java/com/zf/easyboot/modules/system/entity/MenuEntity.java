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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@ApiModel(value = "菜单数据", description = "保存菜单数据")
public class MenuEntity implements Serializable {
    private static final long serialVersionUID = -3243448993491053675L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;
    /**
     * 组件目录
     */
    @ApiModelProperty(value = "组件目录")
    private String component;
    /**
     * 上级菜单ID
     */
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;
    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址")
    @NotEmpty(message = "链接地址不能为空")
    private String path;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sort;
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
