package com.zf.easyboot.modules.system.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.common.utils.ResponseFileUtil;
import com.zf.easyboot.config.FileConfig;
import com.zf.easyboot.config.util.FileOperateUtil;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.excel.DictExcelEntity;
import com.zf.easyboot.modules.system.service.DictService;
import com.zf.easyboot.modules.system.vo.DictSearchVo;
import com.zf.easyboot.security.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "字典表", tags = "字典表详细")
@RestController
@RequestMapping("/system/dict")
public class DictController {

    @Resource
    private DictService dictService;


    @Resource
    private FileOperateUtil fileOperateUtil;

    /**
     * 查询字典列表信息
     *
     * @param dictSearchVo
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_ALL','DICT_SELECT')")
    @ApiOperation("获取字典全部信息")
    public ApiMessage list(@RequestBody DictSearchVo dictSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictSearchVo);
        PageUtils page = dictService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    /**
     * 保存字典信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_CREATE')")
    @ApiOperation("保存字典表")
    public ApiMessage save(@RequestBody DictEntity dictEntity) {
        dictService.save(dictEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改字典信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_EDIT')")
    @ApiOperation("修改字典表")
    public ApiMessage update(@RequestBody DictEntity dictEntity) {
        dictService.updateById(dictEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    /**
     * 删除字典信息
     *
     * @param id
     * @return
     */
    @SysLog("删除字典表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        dictService.deleteById(id);

        return ApiMessage.ofSuccess();
    }


    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_IMPORTEXCEL')")
    @ApiOperation("批量导入字典表")
    @SysLog(value = "批量导入字典表")
    public ApiMessage importExcel(@RequestParam("file") MultipartFile file) {
        String filePath = fileOperateUtil.importFile(file);


        return dictService.importExcelData(filePath);

    }


    /**
     * 导出excel
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_EXPORTEXCEL')")
    @ApiOperation("字典表批量导出")
    @SysLog("字典表批量导出")
    public void exportExcel(@RequestBody DictSearchVo dictSearchVo, HttpServletResponse response) throws IOException {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictSearchVo);
        List<DictExcelEntity> list = dictService.exportExcel(params);
        ExportParams paramsExcel = new ExportParams(null, "字典表", ExcelType.XSSF);
        //是否固定表头
        paramsExcel.setFreezeCol(1);
        String name = "字典表.xlsx";
        Workbook workbook = ExcelExportUtil.exportExcel(paramsExcel, DictExcelEntity.class, list);
        ResponseFileUtil.exportExcelFile(response, workbook, name);
    }


}
