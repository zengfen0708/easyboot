package com.zf.easyboot.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.entity.MenuEntity;
import com.zf.easyboot.modules.system.service.MenuService;
import com.zf.easyboot.modules.system.vo.MenuVo;
import com.zf.easyboot.modules.system.vo.TreeVo;
import com.zf.easyboot.security.jwt.JwtUser;
import com.zf.easyboot.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单模块
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/29.
 */
@Slf4j
@Api(value = "菜单目录", tags = "生成菜单目录")
@RestController
@RequestMapping("/system/menus")
public class MenuController {


    @Resource
    private MenuService menuService;

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @RequestMapping(value = "/build", method = RequestMethod.GET)
    @ApiOperation(value = "初始化导航菜单",
            notes = "初始化导航菜单")
    public ApiMessage buildMenus() {

        //获取当前系统的用户
        JwtUser jwtUser = SecurityUtil.getCurrentUser();

        String roles = jwtUser.getRoleIds();
        List<MenuVo> list = menuService.initMenuTree(roles);


        return ApiMessage.ofSuccess(list);
    }

    /**
     * 返回全部的菜单
     *
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SUPER')")
    public ApiMessage getMenuTree() {
        List<TreeVo> treeList = menuService.getMenuTree();

        return ApiMessage.ofSuccess(treeList);
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @SysLog(value = "查询菜单")
    @RequestMapping(value = "/initMenusAll", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER')")
    public ApiMessage initMenusAll(@RequestBody Map<String, Object> params) {
        PageUtils pageUtils = menuService.queryAllMenu(params);

        return ApiMessage.ofSuccess(pageUtils);
    }


    @SysLog(value = "新增菜单")
    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyRole('SUPER','MENU_CREATE')")
    public ApiMessage save(@Validated @RequestBody MenuEntity menuEntity) {
        ApiMessage r = menuService.saveMenuInfo(menuEntity);

        return r;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','MENU_EDIT')")
    @ApiOperation("修改菜单")
    public ApiMessage update(@RequestBody MenuEntity menuEntity) {
        menuService.updateById(menuEntity);//全部更新

        return ApiMessage.ofSuccess();
    }
}
