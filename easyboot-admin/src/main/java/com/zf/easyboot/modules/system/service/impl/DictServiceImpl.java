package com.zf.easyboot.modules.system.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.excel.DictExcelVo;
import com.zf.easyboot.modules.system.mapper.DictMapper;
import com.zf.easyboot.modules.system.service.DictDetailService;
import com.zf.easyboot.modules.system.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Resource
    private DictDetailService dictDetailService;

    @Override
    public PageUtils queryList(Map<String, Object> params) {
        Integer currPage = ConverterConstant.converterPageInfo.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterPageInfo.convert(params.get("size"));
        currPage = Optional.ofNullable(currPage).orElse(CommonConstant.DEFAULT_PAGE);
        pageSize = Optional.ofNullable(pageSize).orElse(CommonConstant.DEFAULT_PAGE_SIZE);
        Integer startPage = currPage == 0 ? currPage * pageSize : (currPage - 1) * pageSize;

        List<DictEntity> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(list, totalCount, pageSize, currPage);


        return page;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        //删除字典表
        DictEntity dictEntity = new DictEntity();
        dictEntity.setDeleted(CommonConstant.INVALIDDELETE);
        baseMapper.update(dictEntity, new QueryWrapper<DictEntity>().eq("id", id));

        //然后输出字典详情表
        DictDetailEntity dictDetailEntity = new DictDetailEntity();
        dictDetailEntity.setDeleted(CommonConstant.INVALIDDELETE);
        dictDetailService.update(dictDetailEntity, new QueryWrapper<DictDetailEntity>().eq("dict_id", id));

    }

    /**
     * 导出excel数据
     *
     * @param params
     * @return
     */
    @Override
    public List<DictExcelVo> exportExcel(Map<String, Object> params) {

        return baseMapper.exportExcel(params);
    }

    /**
     * 导入excel数据
     *
     * @param filePath
     * @return
     */
    @Override
    public ApiMessage importExcelData(String filePath) {
        ImportParams params = new ImportParams();
        params.setHeadRows(CommonConstant.EXCELHEADROWS);//设置读取的开始行

        //读取excel文件
        List<DictExcelVo> excelList = ExcelImportUtil.importExcel(
                new File(filePath), DictExcelVo.class, params);

        if (!CollectionUtils.isEmpty(excelList)) {
            // 多线程问题
            List<DictEntity> list = excelList.parallelStream()
                    .map(item -> initDictEntiry(item)).collect(Collectors.toList());
            super.saveBatch(list, CommonConstant.SQLBATCHNUM);

            return ApiMessage.ofSuccess();
        }

        return ApiMessage.putHttpStatus(HttpStatus.FILE_IMPORT_ERROR);
    }

    /**
     * 初始化数据
     * @param dictExcelEntity
     * @return
     */
    private DictEntity initDictEntiry(DictExcelVo dictExcelEntity) {
        DictEntity dictEntity = new DictEntity();
        BeanCopierUtils.copyProperties(dictExcelEntity, dictEntity);
        return dictEntity;
    }
}
