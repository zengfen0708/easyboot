package com.zf.easyboot.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树形菜单
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/10.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeVo implements Serializable {
    private static final long serialVersionUID = -7556527517438945793L;

    private Long id;
    private String label;
    private List<TreeVo> children;

}
