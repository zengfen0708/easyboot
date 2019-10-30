package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.excel.DictExcelVo;

import java.util.List;
import java.util.Map;


/**
 * 字典表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DictService extends IService<DictEntity> {

    PageUtils queryList(Map<String,Object> params);

    void deleteById(Long id);

    List<DictExcelVo> exportExcel(Map<String,Object> params);

    ApiMessage importExcelData(String filePath);
}

