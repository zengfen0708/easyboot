package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return
     */
    Optional<UserEntity> findByUsername(@Param("username") String username);

    /**
     * 查询所有用户信息
     * @param startPage
     * @param pageSize
     * @param params
     * @return
     */
    List<UserVo> queryList(@Param("startPage") Integer startPage,
                           @Param("pageSize") Integer pageSize,
                           @Param("params") Map<String,Object> params);


    /**
     * 查询用户汇总数
     * @param params
     * @return
     */
    Integer queryListTotal(@Param("params")Map<String,Object> params);
}
