package com.zf.easyboot.modules.system.controller;

import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import com.zf.easyboot.modules.system.service.PermissionService;
import com.zf.easyboot.modules.system.vo.PermissionSearchVo;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "权限菜单", tags = "权限管理")
@RestController
@RequestMapping("/system/permissions")
public class PermissionController {


    @Resource
    private PermissionService permissionService;

    @SysLog("获取权限菜单信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','PERMISSION_ALL')")
    @ApiOperation("获取权限菜单信息")
    public ApiMessage list(@RequestBody PermissionSearchVo permissionSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(permissionSearchVo);
        PageUtils page = permissionService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    @SysLog("加载添加权限权限树形菜单")
    @RequestMapping(value = "/treeBulid", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','PERMISSION_ALL')")
    @ApiOperation("加载添加权限权限树形菜单")
    public ApiMessage treeBulid(@RequestBody PermissionSearchVo permissionSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(permissionSearchVo);
        List<TreeNodeAddVo> data = permissionService.treeBulid(params);

        return ApiMessage.ofSuccess(data);
    }


    /**
     * 保存
     */
    @SysLog("保存权限菜单")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','PERMISSION_SAVE')")
    @ApiOperation("保存权限菜单")
    public ApiMessage save(@RequestBody PermissionEntity permissionEntity) {
        permissionService.save(permissionEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @SysLog("修改权限数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','PERMISSION_EDIT')")
    @ApiOperation("修改权限数据")
    public ApiMessage update(@RequestBody PermissionEntity permissionEntity) {
        permissionService.updateById(permissionEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','PERMISSION_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        permissionService.deleteById(id);

        return ApiMessage.ofSuccess();
    }

}
