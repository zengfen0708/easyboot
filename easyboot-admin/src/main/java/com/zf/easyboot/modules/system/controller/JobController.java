package com.zf.easyboot.modules.system.controller;

import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.JobEntity;
import com.zf.easyboot.modules.system.service.JobService;
import com.zf.easyboot.modules.system.vo.JobSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Api(value = "岗位信息",tags = "岗位信息")
@RestController
@RequestMapping("/system/job")
public class JobController {


    @Resource
    private JobService jobService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USERJOB_ALL','USERJOB_SELECT')")
    @ApiOperation("获取岗位列表信息")
    public ApiMessage list(@RequestBody JobSearchVo jobSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(jobSearchVo);
        PageUtils page = jobService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }



    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USERJOB_CREATE')")
    @ApiOperation("保存权限菜单")
    public ApiMessage save(@RequestBody JobEntity jobEntity) {
        jobService.save(jobEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USERJOB_EDIT')")
    @ApiOperation("修改权限数据")
    public ApiMessage update(@RequestBody JobEntity jobEntity) {
        jobService.updateById(jobEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','USERJOB_ALL','USERJOB_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        jobService.deleteById(id);

        return ApiMessage.ofSuccess();
    }
}
