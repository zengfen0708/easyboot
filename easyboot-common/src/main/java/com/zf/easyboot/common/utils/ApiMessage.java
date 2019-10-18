package com.zf.easyboot.common.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/18.
 */
@ApiModel(description = "返回接口类")
@Data
@NoArgsConstructor
public class ApiMessage<T> implements Serializable {
    private static final long serialVersionUID = 1563160751659518345L;


    @ApiModelProperty(value = "返回状态", required = true)
    private Integer code;

    @ApiModelProperty(value = "返回描述", required = true)
    private String message;

    @ApiModelProperty(value = "返回对象")
    private T data;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime nowTime;


    /**
     * 返回 ResponseMessage
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>
     * @return
     */
    public static <T> ApiMessage<T> putData(Integer code, T data, String msg) {
        return new ApiMessage<>(code, data, msg);
    }

    public static <T> ApiMessage<T> putData(HttpStatus httpStatus, T data) {
        return new ApiMessage(httpStatus, data);
    }

    public static ApiMessage putHttpStatus(HttpStatus httpStatus) {

        return new ApiMessage(httpStatus);
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     * @return ApiResponse
     */
    public static <T> ApiMessage<T> of(Integer code, String message, T data) {
        return new ApiMessage(code, data, message);
    }

    /**
     * 构造一个成功且不带数据的API返回
     *
     * @return ApiResponse
     */
    public static ApiMessage ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * 构造一个成功且带数据的API返回
     *
     * @param data 返回内容
     * @return ApiResponse
     */
    public static <T> ApiMessage<T> ofSuccess(T data) {

        return putData(HttpStatus.SUCCESS.getCode(), data,
                HttpStatus.SUCCESS.getMessage());
    }


    /**
     * 构造一个异常的API返回
     *
     * @param t   异常
     * @param <T> {@link BaseException} 的子类
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiMessage<T> ofException(T t) {
        return putData(t.getCode(), null, t.getMessage());
    }


    private ApiMessage(HttpStatus resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }


    private ApiMessage(HttpStatus resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private ApiMessage(HttpStatus resultCode, T data, String message) {
        this(resultCode.getCode(), null, message);
    }

    private ApiMessage(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        nowTime= LocalDateTime.now();
    }


}
