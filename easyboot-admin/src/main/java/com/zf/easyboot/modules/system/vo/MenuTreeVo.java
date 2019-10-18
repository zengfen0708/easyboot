package com.zf.easyboot.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/9.
 */
@Data
public class MenuTreeVo {

    private Long id;

    private String name;

    private Long sort;

    private String path;

    private String component;

    private Long parentId;


    private String icon;

    private List<MenuTreeVo> childrens;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
}
