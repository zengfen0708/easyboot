package com.zf.easyboot.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import com.zf.easyboot.modules.system.service.DictDetailService;
import com.zf.easyboot.modules.system.vo.DictDetailSearchVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典详情
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "字典详情", tags = "字典详情")
@RestController
@RequestMapping("/system/dictDetail")
public class DictDetailController {

    @Resource
    private DictDetailService dictDetailService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_ALL')")
    @ApiOperation("获取字典详情全部信息")
    public ApiMessage list(@RequestBody DictDetailSearchVo dictDetailSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictDetailSearchVo);
        PageUtils page = dictDetailService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    @RequestMapping(value = "/queryDictDetailsInfo", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_ALL')")
    @ApiOperation("获取字典详情信息")
    public ApiMessage queryDictDetailsInfo(@RequestBody DictDetailSearchVo dictDetailSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictDetailSearchVo);
        List<DictDetailEntity> data = dictDetailService.queryDictDetailsInfo(params);

        return ApiMessage.ofSuccess(data);
    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_CREATE')")
    @ApiOperation("保存字典详情数据")
    public ApiMessage save(@RequestBody DictDetailEntity dictDetailEntity) {
        dictDetailService.save(dictDetailEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_EDIT')")
    @ApiOperation("修改字典详情数据")
    public ApiMessage update(@RequestBody DictDetailEntity dictDetailEntity) {
        dictDetailService.updateById(dictDetailEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除字典详情")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICT_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        dictDetailService.deleteById(id);

        return ApiMessage.ofSuccess();
    }

}
