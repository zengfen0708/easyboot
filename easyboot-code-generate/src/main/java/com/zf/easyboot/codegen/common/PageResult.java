package com.zf.easyboot.codegen.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页结果集
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    private Long total;

    /**
     * 页码
     */
    private int pageNumber;

    /**
     * 每页结果数
     */
    private int pageSize;

    /**
     * 结果集
     */
    private List<T> list;
}
