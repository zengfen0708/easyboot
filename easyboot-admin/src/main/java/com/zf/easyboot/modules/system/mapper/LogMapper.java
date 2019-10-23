package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.LogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/21.
 */
public interface LogMapper extends BaseMapper<LogEntity> {

    List<LogEntity> queryList(@Param("startPage") Integer startPage,
                              @Param("pageSize") Integer pageSize,
                              @Param("params") Map<String,Object> params);

    Integer queryListTotal( @Param("params")Map<String,Object> params);


    String queryErrorDetail(@Param("params") Map<String,Object> params);
}
