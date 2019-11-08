package com.zf.easyboot.codegen.service;

import cn.hutool.db.Entity;

import com.zf.easyboot.codegen.common.PageResult;
import com.zf.easyboot.codegen.vo.GenConfig;
import com.zf.easyboot.codegen.vo.TableRequest;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
public interface CodeGenService {

    /**
     * 生成代码
     *
     * @param genConfig 生成配置
     * @return 代码压缩文件
     */
    byte[] generatorCode(GenConfig genConfig);

    /**
     * 分页查询表信息
     *
     * @param request 请求参数
     * @return 表名分页信息
     */
    PageResult<Entity> list(TableRequest request);
}
