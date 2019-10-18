package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 获取所有的权限菜单
     * @param roleIds 权限
     * @return
     */
    public List<MenuEntity> findByRoleMenuTree(@Param("roleIds") String roleIds);


    public  List<MenuEntity> findAllMenuTree(@Param("params") Map<String, Object> params);



}
