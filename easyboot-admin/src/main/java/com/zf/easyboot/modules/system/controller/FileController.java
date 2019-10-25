package com.zf.easyboot.modules.system.controller;

import cn.hutool.core.io.FileUtil;
import com.zf.easyboot.common.annotation.SysLog;
import com.zf.easyboot.config.FileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * 下载文件帮助类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/24.
 */
@Slf4j
@Controller
@RequestMapping("/common/sysFile")
public class FileController {

    @Resource
    private FileConfig fileConfig;

    @SysLog(value = "下载模板文件")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    ResponseEntity<InputStreamResource> downloadFile(String fileName)
            throws IOException {
        InputStream is = FileUtil.getInputStream(fileConfig.getDownPath() + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();
        String filename = URLEncoder.encode(fileName, "UTF-8");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(is));
    }

}
