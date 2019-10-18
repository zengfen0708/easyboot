package com.zf.easyboot.modules.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限是通过后端初始化，暂时可以不需要添加role
 * meta: {
 * title: 'Permission',
 * icon: 'lock',
 * roles: ['admin', 'editor'] // you can set roles in root nav
 * }
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaVo {

    private String title;
    private String icon;
}
