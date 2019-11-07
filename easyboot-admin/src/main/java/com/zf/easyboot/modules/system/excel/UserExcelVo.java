package com.zf.easyboot.modules.system.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Data
public class UserExcelVo implements Serializable {

    /**
     * 用户名
     */
    @Excel(name = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;
    /**
     * 手机
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;


    @Excel(name = "部门名称")
    private String deptName;

    @Excel(name = "岗位名称")
    private String jobName;

    /**
     * 状态，启用-1，禁用-0
     */
    @Excel(name = "状态（启用-1，停用-0)",replace = {"启用_1","停用_0"})
    private Integer status;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
