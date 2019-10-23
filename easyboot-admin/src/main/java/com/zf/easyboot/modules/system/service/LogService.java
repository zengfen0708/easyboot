package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.LogEntity;

import java.util.Map;


/**
 * 系统日志表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-21 19:58:52
 */
public interface LogService extends IService<LogEntity> {

    void saveLog(LogEntity logEntity);

    PageUtils queryList(Map<String,Object> params);

    String getErrorDetail(Map<String, Object> params);
}

