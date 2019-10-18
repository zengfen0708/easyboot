package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.DictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DictMapper extends BaseMapper<DictEntity> {

    List<DictEntity> queryList(@Param("startPage") Integer startPage,
                               @Param("pageSize") Integer pageSize,
                               @Param("params") Map<String, Object> params);

    Integer queryListTotal(@Param("params") Map<String, Object> params);
}
