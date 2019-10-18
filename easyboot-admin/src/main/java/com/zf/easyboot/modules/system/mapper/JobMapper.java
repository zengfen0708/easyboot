package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.JobEntity;
import com.zf.easyboot.modules.system.vo.JobVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 岗位表
 * 
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface JobMapper extends BaseMapper<JobEntity> {



    List<JobVo> queryList(@Param("startPage") Integer startPage,
                          @Param("pageSize") Integer pageSize,
                          @Param("params") Map<String,Object> params);

    Integer queryListTotal( @Param("params")Map<String,Object> params);
}
