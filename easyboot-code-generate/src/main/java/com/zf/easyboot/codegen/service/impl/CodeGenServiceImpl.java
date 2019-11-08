package com.zf.easyboot.codegen.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import com.zaxxer.hikari.HikariDataSource;
import com.zf.easyboot.codegen.common.PageResult;
import com.zf.easyboot.codegen.service.CodeGenService;
import com.zf.easyboot.codegen.utils.CodeGenUtil;
import com.zf.easyboot.codegen.utils.DbUtil;
import com.zf.easyboot.codegen.vo.GenConfig;
import com.zf.easyboot.codegen.vo.TableRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Service
public class CodeGenServiceImpl implements CodeGenService {


    private final String TABLE_SQL_TEMPLATE = "select table_name tableName, engine, table_comment tableComment, " +
            "create_time createTime from information_schema.tables where table_schema = (select database()) %s order by create_time desc";

    private final String COLUMN_SQL_TEMPLATE = "select column_name columnName, data_type dataType, column_comment " +
            "columnComment, column_key columnKey, extra from information_schema.columns where table_name = ? " +
            "and table_schema = (select database()) order by ordinal_position";

    private final String COUNT_SQL_TEMPLATE = "select count(1) from (%s)tmp";

    private final String PAGE_SQL_TEMPLATE = " limit ?,?";

    /**
     * 分页查询表信息
     * @param request 请求参数
     * @return 表名分页信息
     */
    @Override
    @SneakyThrows
    public PageResult<Entity> list(TableRequest request) {
        HikariDataSource dataSource= DbUtil.buildFromTableRequest(request);

        Db db=new Db(dataSource);

        Page page=new Page(request.getCurrentPage(),request.getPageSize());
        int start=page.getStartPosition();
        int pageSize=page.getPageSize();

        String paramSql= StrUtil.EMPTY;
        String tableName = request.getTableName();
        if(StrUtil.isNotBlank(tableName)){
            paramSql = "and table_name like  concat('%', ?, '%')";
        }

        String sql = String.format(TABLE_SQL_TEMPLATE, paramSql);
        String countSql = String.format(COUNT_SQL_TEMPLATE, sql);

        List<Entity> query;
        BigDecimal count;
        if (StrUtil.isNotBlank(tableName)) {
            query = db.query(sql + PAGE_SQL_TEMPLATE, request.getTableName(), start, pageSize);
            count = (BigDecimal) db.queryNumber(countSql, request.getTableName());
        }else {
            query = db.query(sql + PAGE_SQL_TEMPLATE, start, pageSize);
            count = (BigDecimal) db.queryNumber(countSql);
        }
        PageResult<Entity> pageResult = new PageResult<Entity>(count.longValue(), page.getPageNumber(), page.getPageSize(), query);

        dataSource.close();

        return pageResult;
    }

    /**
     *生成代码
     * @param genConfig 生成配置
     * @return
     */
    @Override
    public byte[] generatorCode(GenConfig genConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //查询表信息
        Entity table = queryTable(genConfig.getRequest());
        //查询列信息
        List<Entity> columns = queryColumns(genConfig.getRequest());
        //生成代码
        CodeGenUtil.generatorCode(genConfig, table, columns, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    @SneakyThrows
    private Entity queryTable(TableRequest request) {
        HikariDataSource dataSource = DbUtil.buildFromTableRequest(request);
        Db db = new Db(dataSource);

        String paramSql = StrUtil.EMPTY;
        if (StrUtil.isNotBlank(request.getTableName())) {
            paramSql = "and table_name = ?";
        }
        String sql = String.format(TABLE_SQL_TEMPLATE, paramSql);
        Entity entity = db.queryOne(sql, request.getTableName());

        dataSource.close();
        return entity;
    }

    @SneakyThrows
    private List<Entity> queryColumns(TableRequest request) {
        HikariDataSource dataSource = DbUtil.buildFromTableRequest(request);
        Db db = new Db(dataSource);

        List<Entity> query = db.query(COLUMN_SQL_TEMPLATE, request.getTableName());

        dataSource.close();
        return query;
    }

}
