package com.zf.easyboot.config.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.exception.BaseException;
import com.zf.easyboot.config.FileConfig;
import com.zf.easyboot.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * 使用文件工具类，处理对应的上传附件
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
@Slf4j
@Component
public class FileOperateUtil {

    @Resource
    private FileConfig fileConfig;

    /**
     * 导入文件,并且将文件名打乱
     *
     * @param file
     * @return 返回上传的文件路径
     */
    public String importFile(MultipartFile file) {
        //获取上传的文件名
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new BaseException(HttpStatus.FILE_ERROR));
        fileName = renameToUUID(fileName);

        String username = SecurityUtil.getCurrentUsername();//如果获取当前用户为空，则使用默认的anonymous
        String path = fileConfig.getUploadPath() + File.separator + username;
        //创建目录
        FileUtil.mkdir(path);
        String filePath = path + File.separator + fileName;

        try {
            IoUtil.write(new FileOutputStream(filePath), true, file.getBytes());
        } catch (IOException e) {
            throw new BaseException(HttpStatus.FILE_ERROR);
        }
        File processFile = FileUtil.file(filePath);

        if (!processFile.isFile()) {
            throw new BaseException(HttpStatus.FILE_ERROR);
        }
        if (log.isInfoEnabled()) {
            log.info("当前用户:{}上传的文件路径:{}文件类型:{}", username, filePath,FileTypeUtil.getType(processFile));
        }

        return filePath;
    }

    /**
     * 打乱生成文件名
     *
     * @param fileName 文件名
     * @return
     */
    private String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + StringUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
    }


}
