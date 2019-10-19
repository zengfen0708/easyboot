package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 查询角色列表
     * @param startPage
     * @param pageSize
     * @param params
     * @return
     */
    List<RoleEntity> queryList(@Param("startPage") Integer startPage,
                           @Param("pageSize") Integer pageSize,
                           @Param("params") Map<String,Object> params);


    Integer queryListTotal(@Param("params") Map<String,Object> params);
}
