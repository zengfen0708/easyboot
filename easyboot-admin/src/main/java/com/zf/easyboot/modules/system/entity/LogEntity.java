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
 * 系统日志表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-21 19:58:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_log")
@ApiModel(value = "保存系统日志表数据")
public class LogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 操作用户
     */
    @ApiModelProperty(value = "操作用户")
    private String username;
    /**
     * 方法描述
     */
    @ApiModelProperty(value = "方法描述")
    private String description;
    /**
     * 异常描述
     */
    @ApiModelProperty(value = "异常描述")
    private String exceptionDetail;
    /**
     * 请求方法名
     */
    @ApiModelProperty(value = "请求方法名")
    private String method;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;
    /**
     * 日志类型(0 系统日志 1 异常日志)
     */
    @ApiModelProperty(value = "日志类型(0 系统日志 1 异常日志)")
    private Integer logType;
    /**
     * 请求ip
     */
    @ApiModelProperty(value = "请求ip")
    private String requestIp;
    /**
     * 执行时长(毫秒)
     */
    @ApiModelProperty(value = "执行时长(毫秒)")
    private Long time;
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
