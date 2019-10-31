package com.zf.easyboot.modules.system.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.common.utils.ResponseFileUtil;
import com.zf.easyboot.config.util.FileOperateUtil;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.excel.UserExcelVo;
import com.zf.easyboot.modules.system.service.UserService;
import com.zf.easyboot.modules.system.vo.UserSearchVo;
import com.zf.easyboot.modules.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 系统用户模块
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "用户模块", tags = "用户模块")
@RestController
@RequestMapping("/system/user")
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    private FileOperateUtil fileOperateUtil;

    @SysLog("获取全部信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USER_ALL')")
    @ApiOperation("获取全部信息")
    public ApiMessage list(@RequestBody UserSearchVo userSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(userSearchVo);
        PageUtils page = userService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }
    /**
     * 保存
     */
    @SysLog("保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','ROLES_SAVE')")
    @ApiOperation("保存用户信息表")
    public ApiMessage save(@RequestBody UserVo userVo) {

         userService.handleUserInfo(userVo);

        return ApiMessage.ofSuccess();
    }

    /**
     * 修改
     */
    @SysLog("修改用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USER_EDIT')")
    @ApiOperation("保存角色表")
    public ApiMessage update(@RequestBody UserVo userVo) {
        userService.handleUserInfo(userVo);

        return ApiMessage.ofSuccess();
    }

    /**
     * 删除
     */
    @SysLog("删除用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USER_DELETE')")
    @ApiOperation("删除用户")
    public ApiMessage delete(@PathVariable Long id) {
        userService.delete(id);//删除用户

        return ApiMessage.ofSuccess();
    }


    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USER_IMPORTEXCEL')")
    @ApiOperation("批量导入用户表")
    @SysLog(value = "批量导入用户表")
    public ApiMessage importExcel(@RequestParam("file") MultipartFile file) {
        String filePath = fileOperateUtil.importFile(file);


        return userService.importExcelData(filePath);
    }

    /**
     * 导出excel
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USER_EXPORTEXCEL')")
    @ApiOperation("用户表批量导出")
    @SysLog("用户表批量导出")
    public void exportExcel(@RequestBody UserSearchVo userSearchVo, HttpServletResponse response) throws IOException {
        Map<String, Object> params = BeanCopierUtils.object2Map(userSearchVo);
        List<UserEntity> list = userService.exportExcel(params);
        ExportParams paramsExcel = new ExportParams(null, "用户表", ExcelType.XSSF);
        //是否固定表头
        paramsExcel.setFreezeCol(0);
        String name = "用户表.xlsx";
        Workbook workbook = ExcelExportUtil.exportExcel(paramsExcel, UserExcelVo.class, list);
        ResponseFileUtil.exportExcelFile(response, workbook, name);
    }
}
