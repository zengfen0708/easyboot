package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单关系表
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/20.
 */
@Mapper
public interface RoleMenuMapper  extends BaseMapper<RoleMenuEntity> {
}
