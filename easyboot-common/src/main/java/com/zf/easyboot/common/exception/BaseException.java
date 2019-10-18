package com.zf.easyboot.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.zf.easyboot.common.enums.HttpStatus;

/**
 * 异常基类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private Integer code;
    private String message;
    private Object data;

    public BaseException(HttpStatus httpStatus) {
        super(httpStatus.getMessage());
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
    }

    public BaseException(HttpStatus httpStatus, Object data) {
        this(httpStatus);
        this.data = data;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Object data) {
        this(code, message);
        this.data = data;
    }

}
