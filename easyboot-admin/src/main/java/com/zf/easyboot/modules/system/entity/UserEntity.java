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
 * @date 2019/9/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@ApiModel(value = "用户表数", description = "保存用户表数")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -211649679857910849L;
    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 性别，男-1，女-2
     */
    @ApiModelProperty(value = "性别，男-1，女-2")
    private Integer sex;

    @ApiModelProperty(value = "部门id")
    private Long deptId;


    @ApiModelProperty(value = "岗位id")
    private Long jobId;
    /**
     * 状态，启用-1，禁用-0
     */
    @ApiModelProperty(value = "状态，启用-1，禁用-0")
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
