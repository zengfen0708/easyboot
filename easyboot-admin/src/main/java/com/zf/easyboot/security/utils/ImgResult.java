package com.zf.easyboot.security.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgResult {
    private String img;

    private String uuid;

    private Integer code;
}
