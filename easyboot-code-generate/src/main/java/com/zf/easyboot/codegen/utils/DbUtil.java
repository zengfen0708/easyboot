package com.zf.easyboot.codegen.utils;

import com.zaxxer.hikari.HikariDataSource;
import com.zf.easyboot.codegen.vo.TableRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库工具类
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Slf4j
@UtilityClass
public class DbUtil {

    public HikariDataSource buildFromTableRequest(TableRequest request) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(request.getPrepend() + request.getUrl());
        dataSource.setUsername(request.getUsername());
        dataSource.setPassword(request.getPassword());
        return dataSource;
    }
}

