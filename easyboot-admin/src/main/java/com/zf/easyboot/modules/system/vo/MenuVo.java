package com.zf.easyboot.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单初始化
 * <p>
 * path: '/permission',
 * component: Layout,
 * redirect: '/permission/page',
 * alwaysShow: true, // will always show the root menu
 * name: 'Permission',
 * meta: {
 * title: 'Permission',
 * icon: 'lock',
 * roles: ['admin', 'editor'] // you can set roles in root nav
 * },
 * {
 * path: 'external-link',
 * component: Layout,
 * children: [
 * {
 * path: 'https://github.com/PanJiaChen/vue-element-admin',
 * meta: { title: 'External Link', icon: 'link' }
 * }
 * ]
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo implements Serializable {

    private Long id;
    private Long parentId;
    private String path;
    private String component;
    private String name;
    private String redirect;

    private boolean alwaysShow;
    private MetaVo meta;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    private List<MenuVo> childrens;

}
