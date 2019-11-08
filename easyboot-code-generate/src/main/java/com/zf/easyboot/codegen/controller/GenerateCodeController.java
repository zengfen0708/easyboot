package com.zf.easyboot.codegen.controller;

import cn.hutool.core.io.IoUtil;
import com.zf.easyboot.codegen.common.ApiMessage;
import com.zf.easyboot.codegen.service.CodeGenService;
import com.zf.easyboot.codegen.vo.GenConfig;
import com.zf.easyboot.codegen.vo.TableRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 批量生成代码
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/29.
 */
@RestController
@RequestMapping("/generator")
@Slf4j
public class GenerateCodeController {

    @Resource
    private CodeGenService codeGenService;

    /**
     * 列表
     *
     * @param request 参数集
     * @return 数据库表
     */
    @GetMapping("/table")
    public ApiMessage listTables(TableRequest request) {
        return ApiMessage.success(codeGenService.list(request));
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    @PostMapping("")
    public void generatorCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
        byte[] data = codeGenService.generatorCode(genConfig);

        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }

}
