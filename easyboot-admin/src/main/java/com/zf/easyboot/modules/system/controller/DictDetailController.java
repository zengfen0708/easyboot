package com.zf.easyboot.modules.system.controller;

import com.google.common.base.Splitter;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import com.zf.easyboot.modules.system.service.DictDetailService;
import com.zf.easyboot.modules.system.vo.DictDetailSearchVo;
import com.zf.easyboot.modules.system.vo.DictDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_ALL')")
    @ApiOperation("获取字典详情全部信息")
    public ApiMessage list(@RequestBody DictDetailSearchVo dictDetailSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictDetailSearchVo);
        PageUtils page = dictDetailService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    @RequestMapping(value = "/queryDictDetailsInfo", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_ALL')")
    @ApiOperation("获取字典详情信息")
    public ApiMessage queryDictDetailsInfo(@RequestBody DictDetailSearchVo dictDetailSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(dictDetailSearchVo);
        List<DictDetailVo> data = dictDetailService.queryDictDetailsInfo(params);

        return ApiMessage.ofSuccess(data);
    }

    @RequestMapping(value = "/queryDictDetailsMap", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_ALL')")
    @ApiOperation("根据字典获取对应的map集合")
    public ApiMessage queryDictDetailsMap(@RequestBody DictDetailSearchVo dictDetailSearchVo) {
        String dictName = dictDetailSearchVo.getDictName();
        //将请求数据分隔
        List<String> dictNameList = Splitter.on(",").omitEmptyStrings().splitToList(dictName);
        if (StringUtils.isNotBlank(dictName)) {
            dictDetailSearchVo.setDictName(null);
        }
        Map<String, Object> params = BeanCopierUtils.object2Map(dictDetailSearchVo);

        params.put("dictNameList", dictNameList);
        List<DictDetailVo> list = dictDetailService.queryDictDetailsInfo(params);

        if (CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(dictNameList)) {

            Map<String, List<DictDetailVo>> data = dictNameList.parallelStream().map(item -> initDictDetailInfo(item))
                    .collect(Collectors.groupingBy(DictDetailVo::getDictName));

            return ApiMessage.ofSuccess(data);
        }
        Map<String, List<DictDetailVo>> data = list.parallelStream().collect(Collectors.groupingBy(DictDetailVo::getDictName));

        return ApiMessage.ofSuccess(data);
    }

    /**
     *  初始化字典详情信息
     * @param key
     * @return
     */
    private DictDetailVo initDictDetailInfo(String key) {
        DictDetailVo dictDetailVo = new DictDetailVo();
        dictDetailVo.setDictName(key);

        return dictDetailVo;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_SAVE')")
    @ApiOperation("保存字典详情数据")
    public ApiMessage save(@RequestBody DictDetailEntity dictDetailEntity) {
        dictDetailService.save(dictDetailEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_EDIT')")
    @ApiOperation("修改字典详情数据")
    public ApiMessage update(@RequestBody DictDetailEntity dictDetailEntity) {
        dictDetailService.updateById(dictDetailEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除字典详情")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DICTDETAIL_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        dictDetailService.deleteById(id);

        return ApiMessage.ofSuccess();
    }

}
