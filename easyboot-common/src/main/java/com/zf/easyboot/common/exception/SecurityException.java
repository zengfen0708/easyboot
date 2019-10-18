package com.zf.easyboot.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.zf.easyboot.common.enums.HttpStatus;

/**
 * 全局异常
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityException extends BaseException {


    public SecurityException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public SecurityException(HttpStatus httpStatus, Object data) {
        super(httpStatus, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
