package com.zf.easyboot.modules.system.vo;

import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
@Data
public class LogSearchVo  extends PageInfoVo  {

    private Integer logType;

    private Long id;

    private String  type;

    private String value;
}
