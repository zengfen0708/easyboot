package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.MenuEntity;
import com.zf.easyboot.modules.system.vo.MenuVo;
import com.zf.easyboot.modules.system.vo.TreeVo;

import java.util.List;
import java.util.Map;


/**
 * 菜单
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-09-30 11:20:49
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 初始化权限菜单
     * @param roleIds
     * @return
     */
    List<MenuVo> initMenuTree( String roleIds );

    /**
     * 查询所有的菜单
     * @param params
     * @return
     */
    PageUtils queryAllMenu(Map<String, Object> params);

    /**
     * 查询所有的树形菜单
     * @return
     */
    List<TreeVo> getMenuTree();

    ApiMessage saveMenuInfo(MenuEntity menuEntity);
}

