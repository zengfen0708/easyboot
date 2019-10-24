package com.zf.easyboot.modules.system.excel;



import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/23.
 */
@Data
public class DictExcelEntity implements Serializable {

    /**
     * 设置：字典名称
     */
    @Excel(name = "字典名称")
    private String name;

    /**
     * 设置：描述
     */
    @Excel(name = "描述")
    private String remark;

    /**
     * 设置：状态，正常-1，停用-0
     * 导出是{a_id,b_id} 导入反过来
     */
    @Excel(name = "状态（正常-1，停用-0)",replace = {"正常_1","停用_0"})
    private Integer status;

    /**
     * 设置：软删除标识(0 有效 1 已删除)
     */
    @Excel(name = "软删除标识(0 有效 1 已删除)",replace = {"有效_0","已删除_1"})
    private Integer deleted;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", format = "yyyy-MM-dd")
    private Date createTime;
}
