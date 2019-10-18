package com.zf.easyboot.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import com.zf.easyboot.modules.system.service.DictService;
import com.zf.easyboot.modules.system.vo.DictSearchVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_ALL','DICT_SELECT')")
    @ApiOperation("获取字典全部信息")
    public ApiMessage list(@RequestBody DictSearchVo dictSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictSearchVo);
        PageUtils page = dictService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_CREATE')")
    @ApiOperation("保存字典表")
    public ApiMessage save(@RequestBody DictEntity dictEntity) {
        dictService.save(dictEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_EDIT')")
    @ApiOperation("修改字典表")
    public ApiMessage update(@RequestBody DictEntity dictEntity) {
        dictService.updateById(dictEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除字典表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        dictService.deleteById(id);

        return ApiMessage.ofSuccess();
    }

}
