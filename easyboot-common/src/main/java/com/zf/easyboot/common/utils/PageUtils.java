package com.zf.easyboot.common.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据封装类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/9.
 */
@Data
@NoArgsConstructor
public class PageUtils implements Serializable {

    //总记录数
    private Integer totalCount;
    //每页记录数
    private Integer pageSize;
    //总页数
    private Integer totalPage;

    //当前页数
    private Integer currPage;
    //列表数据
    private List<?> list;


    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);

    }


}
