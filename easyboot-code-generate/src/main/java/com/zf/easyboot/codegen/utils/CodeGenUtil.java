package com.zf.easyboot.codegen.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.setting.dialect.Props;
import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import com.zf.easyboot.codegen.constants.GenConstants;
import com.zf.easyboot.codegen.entity.ColumnEntity;
import com.zf.easyboot.codegen.entity.TableEntity;
import com.zf.easyboot.codegen.vo.GenConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@Slf4j
@UtilityClass
public class CodeGenUtil {

    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String EXCEL_JAVA_VM = "Excel.java.vm";
    private final String VO_JAVA_VM = "Vo.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";
    private final String API_JS_VM = "api.js.vm";
    private final String INDEX_VUE_VM = "index.vue.vm";
    private final String FROM_VUE_VM = "form.vue.vm";
    private final String MENU_SQL_VM = "menu.sql.vm";
    /**
     * 初始化模版
     *
     * @return
     */
    private List<String> getTemplates() {
        List<String> templates = Lists.newArrayList();
        templates.add("code/Excel.java.vm");
        templates.add("code/Entity.java.vm");
        templates.add("code/Mapper.java.vm");
        templates.add("code/Mapper.xml.vm");
        templates.add("code/Service.java.vm");
        templates.add("code/ServiceImpl.java.vm");
        templates.add("code/Controller.java.vm");
        templates.add("code/Vo.java.vm");

        templates.add("code/api.js.vm");

        templates.add("code/index.vue.vm");

        templates.add("code/form.vue.vm");
        templates.add("code/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public void generatorCode(GenConfig genConfig,
                              Entity table,
                              List<Entity> columns,
                              ZipOutputStream zip) {

        Props props = getConfig();


        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getStr("tableName"));


        //表注释
        String comments = Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getComments()))
                .orElse(table.getStr("tableComment"));

        tableEntity.setComments(comments);

        //别名替换
        String tablePrefix = Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getTablePrefix()))
                .orElse(props.getStr("tablePrefix"));

        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StrUtil.lowerFirst(className));
        Boolean hasBigDecimal = false;
        //初始化列信息
        List<ColumnEntity> columnList =
                columns.parallelStream()
                        .map(item -> buildColumnEntity(item, hasBigDecimal, props, tableEntity))
                        .collect(Collectors.toList());

        tableEntity.setColumns(columnList);

        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }


        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("roleName", StringUtils.upperCase(tableEntity.getCaseClassName()));
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());
        map.put("vueCancel","$refs[scope.row.id].doClose()");
        map.put("vue1","this.$refs[id].doClose()");
        //优先获取请求配置信息，否则从默认加载对应的信息
        map.put("comments", Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getComments()))
                .orElse(tableEntity.getComments()));
        map.put("author", Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getAuthor()))
                .orElse(props.getStr("author")));

        map.put("moduleName", Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getModuleName()))
                .orElse(props.getStr("moduleName")));

        map.put("package", Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getPackageName()))
                .orElse(props.getStr("package")));

        map.put("mainPath", Optional.ofNullable(ConverterConstant.converterStr.convert(genConfig.getPackageName()))
                .orElse(props.getStr("mainPath")));

        VelocityContext context = new VelocityContext(map);

        List<String> templates = getTemplates();

        templates.forEach(item -> {
            writeTemplates(item, context, tableEntity, map, zip);
        });
    }


    private void writeTemplates(String template,
                                VelocityContext context,
                                TableEntity tableEntity,
                                Map<String, Object> map,
                                ZipOutputStream zip) {
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
        tpl.merge(context, sw);

        try {
            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects.requireNonNull(
                    getFileName(template,
                            tableEntity.getCaseClassName(),
                            map.get("package")
                                    .toString(), map.get("moduleName").toString()))));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
        }

    }

    /**
     * 初始化列信息
     *
     * @param column
     * @param hasBigDecimal
     * @param props
     * @param tableEntity
     * @return
     */
    private ColumnEntity buildColumnEntity(Entity column,
                                           Boolean hasBigDecimal,
                                           Props props,
                                           TableEntity tableEntity) {
        ColumnEntity columnEntity = new ColumnEntity();

        columnEntity.setColumnName(column.getStr("columnName"));
        columnEntity.setDataType(column.getStr("dataType"));
        columnEntity.setComments(column.getStr("columnComment"));
        columnEntity.setExtra(column.getStr("extra"));
        //列名转换成Java属性名
        String attrName = columnToJava(columnEntity.getColumnName());
        columnEntity.setCaseAttrName(attrName);
        columnEntity.setLowerAttrName(StrUtil.lowerFirst(attrName));

        //列的数据类型，转换成Java类型
        String attrType = props.getStr(columnEntity.getDataType(), "unknownType");
        columnEntity.setAttrType(attrType);
        if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
            hasBigDecimal = true;
        }
        //是否主键
        if ("PRI".equalsIgnoreCase(column.getStr("columnKey"))
                && tableEntity.getPk() == null) {
            tableEntity.setPk(columnEntity);
        }
        return columnEntity;
    }

    /**
     * 获取系统配置默认信息
     *
     * @return
     */
    private Props getConfig() {
        Props props = new Props("generator.properties");
        props.autoLoad(true);

        return props;
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName
     * @param tablePrefix
     * @return
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StrUtil.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     */
    private String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }


    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        // 包路径
        String packagePath = GenConstants.SIGNATURE + File.separator + "src" +
                File.separator + "main" +
                File.separator + "java" + File.separator;
        // 资源路径
        String resourcePath = GenConstants.SIGNATURE + File.separator + "src"
                + File.separator + "main" + File.separator + "resources" + File.separator;
        // api路径
        String apiPath = GenConstants.SIGNATURE + File.separator + "api" + File.separator
                + "work" + File.separator;

        String vuePath = GenConstants.SIGNATURE + File.separator + "view" + File.separator
                + "work" + File.separator;

        if (StrUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator)
                    + File.separator + moduleName + File.separator;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains(EXCEL_JAVA_VM)) {
            return packagePath + "excel" + File.separator + className + "ExcelVo.java";
        }

        if(template.contains(VO_JAVA_VM)){
            return packagePath + "vo" + File.separator + className + "SearchVo.java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return resourcePath + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains(API_JS_VM)) {
            return apiPath + className.toLowerCase() + ".js";
        }

        if (template.contains(INDEX_VUE_VM)) {
            return vuePath + className.toLowerCase()+File.separator + "index.vue";
        }
        if (template.contains(FROM_VUE_VM)) {
            return vuePath + className.toLowerCase()+File.separator+ "form.vue";
        }
        if (template.contains(MENU_SQL_VM)) {
            return resourcePath + className.toLowerCase()+File.separator+ "menu.sql";
        }

        return null;
    }
}
