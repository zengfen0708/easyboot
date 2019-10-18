package com.zf.easyboot.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import com.zf.easyboot.modules.system.service.DeptService;
import com.zf.easyboot.modules.system.vo.DeptSearchVo;
import com.zf.easyboot.modules.system.vo.TreeNodeAddVo;
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
@Api(value = "部门管理", tags = "部门管理")
@RestController
@RequestMapping("/system/dept")
public class DeptController {


    @Resource
    private DeptService deptService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("获取全部信息")
    public ApiMessage list(@RequestBody DeptSearchVo deptSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(deptSearchVo);
        PageUtils page = deptService.queryList(params);

        return ApiMessage.ofSuccess(page);
    }

    /**
     * 初始化树形菜单
     *
     * @param deptSearchVo
     * @return
     */
    @RequestMapping(value = "getDeptTree", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DEPT_ALL')")
    @ApiModelProperty("获取部门管理tree结构")
    public ApiMessage getDeptTree(@RequestBody DeptSearchVo deptSearchVo) {
        Map<String, Object> params = BeanCopierUtils.object2Map(deptSearchVo);
        List<TreeNodeAddVo> data = deptService.getDeptTree(params);

        return ApiMessage.ofSuccess(data);

    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DEPT_CREATE')")
    @ApiOperation("保存部门数据")
    public ApiMessage save(@RequestBody DeptEntity deptEntity) {
        deptService.save(deptEntity);

        return ApiMessage.ofSuccess();
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DEPT_EDIT')")
    @ApiOperation("修改部门数据")
    public ApiMessage update(@RequestBody DeptEntity deptEntity) {
        deptService.updateById(deptEntity);//全部更新

        return ApiMessage.ofSuccess();
    }


    @SysLog("删除部门")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SUPER','DEPT_ALL','DEPT_DELETE')")
    public ApiMessage delete(@PathVariable Long id) {
        deptService.deleteById(id);

        return ApiMessage.ofSuccess();
    }
}
