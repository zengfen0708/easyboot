package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.modules.system.entity.LogEntity;
import com.zf.easyboot.modules.system.mapper.LogMapper;
import com.zf.easyboot.modules.system.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {


}
