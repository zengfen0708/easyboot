package com.zf.easyboot.modules.system.vo;

import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/22.
 */
@Data
public class DictDetailVo extends DictDetailEntity {

    @ApiModelProperty("字典对应的key")
    private String dictName;
}
