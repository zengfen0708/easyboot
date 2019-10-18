package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * 用户角色关系表
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Long> selectByUserIdToRoles(@Param("userId") Long userId);

}
