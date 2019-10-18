package com.zf.easyboot.common.constant;

/**
 * 系统公用参数
 * (接口中默认是静态常量)
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/16.
 */
public interface CommonConstant {

    Integer DELETED=0;

    Integer INVALIDDELETE=1;
    /**
     * 状态0
     */
    Integer STATUS=0;

    String UTF_8="UTF-8";
    /**
     * 系统名称
     */
    String SYSTEM_NAME = "easyboot";

    String DEFAULT_MESSAGE = "无实体数据";

    /**
     * 用户启用
     */
    Integer ENABLE = 1;
    /**
     * 用户禁用
     */
    Integer DISABLE = 0;

    /**
     * 默认每页条数
     */
    Integer DEFAULT_PAGE_SIZE = 10;


    /**
     * 页面
     */
    Integer DEFAULT_PAGE = 0;
    /**
     *  树形结构父类id
     */
    Long DEFAULT_PARENTID=0L;

    /**
     * 匿名用户 用户名
     */
    String ANONYMOUS_NAME = "匿名用户";

}
