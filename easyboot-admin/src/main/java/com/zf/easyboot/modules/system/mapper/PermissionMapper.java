package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.PermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权限
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> queryAllList(@Param("params") Map<String, Object> params);


    /**
     * 查询角色对应的权限
     *
     * @param roleIds 角色集合
     * @return 权限
     */
    List<String> selectByRolesIdToPermission(@Param("roleIds") String roleIds);

    /**
     * 查询对应下级节点信息
     * @param id
     * @return
     */
    List<Long>  queryByPermissionIds(@Param("id") Long id);

    int updateSonPermissionId(@Param("ids") String  id);

    List<PermissionEntity> findByRolesIdPermission(@Param("roleIds") String roleIds);
}
