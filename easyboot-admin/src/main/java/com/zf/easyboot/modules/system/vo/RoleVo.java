package com.zf.easyboot.modules.system.vo;

import com.zf.easyboot.modules.system.entity.MenuEntity;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import com.zf.easyboot.modules.system.entity.RoleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/16.
 */
@Data
public class RoleVo extends RoleEntity {


    //权限菜单
    @ApiModelProperty(value = "权限集合")
    private List<PermissionEntity> permissionList;


    @ApiModelProperty(value = "菜单集合")
    private List<MenuEntity> menuList;

}
