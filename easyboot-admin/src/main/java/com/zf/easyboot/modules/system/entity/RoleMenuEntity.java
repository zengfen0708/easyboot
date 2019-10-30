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
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
@ApiModel(value = "角色与菜单关系表", description = "保存角色与菜单关系表数据")
public class RoleMenuEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 角色主键
     */
    @ApiModelProperty(value = "角色主键")
    private Long roleId;
    /**
     * 菜单主键
     */
    @ApiModelProperty(value = "菜单主键")
    private Long menuId;
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
