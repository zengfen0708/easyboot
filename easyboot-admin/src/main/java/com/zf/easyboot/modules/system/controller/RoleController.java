package com.zf.easyboot.modules.system.controller;

import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.RoleEntity;
import com.zf.easyboot.modules.system.service.RoleService;
import com.zf.easyboot.modules.system.vo.RoleMenuVo;
import com.zf.easyboot.modules.system.vo.RoleSearchVo;
import com.zf.easyboot.modules.system.vo.RoleVo;
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
@Api(value = "角色管理", tags = "角色管理")
@RequestMapping("/system/role")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_SELECT')")
    @ApiOperation("分页获取全部角色信息")
    public ApiMessage list(@RequestBody RoleSearchVo roleSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(roleSearchVo);
        PageUtils page = roleService.queryList(params);

        return ApiMessage.ofSuccess(page);

    }

    @RequestMapping(value = "/getAllRole", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_SELECT')")
    @ApiOperation("不分页获取全部角色信息")
    public  ApiMessage getAllRole(){
        List<RoleVo> roleVos = roleService.queryRoleAll();
        return ApiMessage.ofSuccess(roleVos);
    }


    @SysLog("根据id重新加载信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_SAVE')")
    public  ApiMessage info(@PathVariable Long id){
        RoleVo roleVo=roleService.findByRoleIdInfo(id);

        return ApiMessage.ofSuccess(roleVo);
    }
    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_CREATE')")
    @ApiOperation("保存角色表")
    public ApiMessage save(@RequestBody RoleEntity roleEntity) {
        roleService.save(roleEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_EDIT')")
    @ApiOperation("修改角色表")
    public ApiMessage update(@RequestBody RoleEntity roleEntity) {
        roleService.updateById(roleEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除角色表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        roleService.deleteById(id);

        return ApiMessage.ofSuccess();
    }



    @SysLog("保存角色信息")
    @RequestMapping(value = "/saveRoleInfo", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_SAVE')")
    public ApiMessage saveRoleInfo(@RequestBody RoleMenuVo roleMenuVo){
        roleService.saveRoleInfo(roleMenuVo);



        return ApiMessage.ofSuccess();
    }
}
