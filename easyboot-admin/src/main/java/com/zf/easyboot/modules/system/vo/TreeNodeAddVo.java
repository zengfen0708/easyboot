package com.zf.easyboot.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/14.
 */
@Data
public class TreeNodeAddVo {

    private Long id;

    private String alias;
    private String name;
    private Long parentId;

    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeNodeAddVo> children;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    public String getLabel() {
        return alias;
    }
}
