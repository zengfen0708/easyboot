package com.zf.easyboot.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
@UtilityClass
public class ResponseFileUtil {


    /**
     * 导出excel文件帮助类
     * @param response
     * @param workbook 对应的工作
     * @param name
     * @throws IOException
     */
    public void exportExcelFile(HttpServletResponse response, Workbook workbook, String name) throws IOException {
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(name.getBytes("UTF-8"), "ISO8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        workbook.write(toClient);
        toClient.flush();
        toClient.close();
    }
}
