package com.zf.easyboot.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 保存角色菜单权限
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/17.
 */
@Data
public class RoleMenuVo implements Serializable {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("存储类型 1 菜单权限 2  操作权限")
    private Integer typeId;

    @ApiModelProperty("菜单权限集合")
    private List<Long> menus;


    @ApiModelProperty("权限菜单集合")
    private List<Long> permissions;
}
