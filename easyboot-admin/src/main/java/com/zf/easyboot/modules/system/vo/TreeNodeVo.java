package com.zf.easyboot.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 返回部门信息或者上下级节点
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/14.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeNodeVo implements Serializable {

    private Long id;

    private String name;
    private String alias;
    private Long parentId;

    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeNodeVo> childrens;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;


}
