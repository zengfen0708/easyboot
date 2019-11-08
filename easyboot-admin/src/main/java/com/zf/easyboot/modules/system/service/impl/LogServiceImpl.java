package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.LogEntity;
import com.zf.easyboot.modules.system.mapper.LogMapper;
import com.zf.easyboot.modules.system.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Slf4j
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {


    /**
     * 异步保存系统日志
     *
     * @param logEntity
     */
    @Override
    @Transactional
    @Async("easyboot_taskExecutor")
    public void saveLog(LogEntity logEntity) {
        baseMapper.insert(logEntity);
    }

    @Override
    public PageUtils queryList(Map<String, Object> params) {
        Integer currPage = ConverterConstant.converterPageInfo.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterPageInfo.convert(params.get("size"));
        if (currPage == null) {
            currPage = CommonConstant.DEFAULT_PAGE;
        }

        if (pageSize == null) {
            pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        }
        Integer startPage = currPage == 0 ? currPage * pageSize : (currPage - 1) * pageSize;

        List<LogEntity> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(list, totalCount, pageSize, currPage);


        return page;
    }


    /**
     * 根据id 去查询对应的异常信息
     * @param params
     * @return
     */
    @Override
    public String getErrorDetail(Map<String, Object> params) {



        return baseMapper.queryErrorDetail(params);
    }

}
