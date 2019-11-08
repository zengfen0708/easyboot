package com.zf.easyboot.codegen.vo;

import lombok.Data;

/**
 * 生成配置
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Data
public class GenConfig {

    /**
     * 请求参数
     */
    private TableRequest request;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表备注
     */
    private String comments;
}
