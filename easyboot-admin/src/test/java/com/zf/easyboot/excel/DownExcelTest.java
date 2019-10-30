package com.zf.easyboot.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.io.FileUtil;

import com.zf.easyboot.modules.system.excel.DictExcelVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 导出excel文档
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
public class DownExcelTest {

    private static  String path="E:"+ File.separator;
    public static void main(String[] args) throws IOException  {

       // DownExcelTest.writeExcel();

        File fileProcess = FileUtil.file(path+"logs\\npm配置.txt");

        System.out.println(fileProcess.isFile());

    }

    /**
     *  写excel
     */
    public  static void writeExcel() throws IOException {
        List<DictExcelVo> list= Lists.newArrayList();
        IntStream.of(1,2,4,5).forEach(item->{
            DictExcelVo dictExcelEntity=new DictExcelVo();
            dictExcelEntity.setName("测试"+item);
            dictExcelEntity.setStatus(0);
            list.add(dictExcelEntity);
        });
        ExportParams paramsExcel = new ExportParams(null, "字典表", ExcelType.XSSF);
        paramsExcel.setFreezeCol(1);

        Workbook workbook = ExcelExportUtil.exportExcel(paramsExcel, DictExcelVo.class, list);

        FileOutputStream fos = new FileOutputStream(path + "/logs/abc2.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
