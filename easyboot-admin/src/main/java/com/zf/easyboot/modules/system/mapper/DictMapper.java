package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.excel.DictExcelVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DictMapper extends BaseMapper<DictEntity> {

    /**
     * 查询汇总数据
     * @param startPage 开始
     * @param pageSize  分页大小
     * @param params 请求参数
     * @return 分页对象集合
     */
    List<DictEntity> queryList(@Param("startPage") Integer startPage,
                               @Param("pageSize") Integer pageSize,
                               @Param("params") Map<String, Object> params);

    /**
     *  查询汇总数据
     * @param params 请求参数
     * @return
     */
    Integer queryListTotal(@Param("params") Map<String, Object> params);

    /**
     * 导出excel
     * @param params
     * @return
     */
    List<DictExcelVo> exportExcel(@Param("params") Map<String,Object> params);


}
