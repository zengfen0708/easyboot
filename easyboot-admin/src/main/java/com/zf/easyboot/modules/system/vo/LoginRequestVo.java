package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/18.
 */
@Data
public class LoginRequestVo implements Serializable {
    /**
     * 用户名或邮箱或手机号
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;


    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "唯一标识", required = true)
    private String uuid;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
