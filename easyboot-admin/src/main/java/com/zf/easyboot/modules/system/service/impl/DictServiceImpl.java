package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import com.zf.easyboot.modules.system.entity.DictEntity;
import com.zf.easyboot.modules.system.mapper.DictMapper;
import com.zf.easyboot.modules.system.service.DictDetailService;
import com.zf.easyboot.modules.system.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Resource
    private DictDetailService dictDetailService;

    @Override
    public PageUtils queryList(Map<String, Object> params) {
        Integer currPage = ConverterConstant.converterInt.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterInt.convert(params.get("size"));
        if (currPage == null) {
            currPage = CommonConstant.DEFAULT_PAGE;
        }

        if (pageSize == null) {
            pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        }
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
        DictDetailEntity  dictDetailEntity=new DictDetailEntity();
        dictDetailEntity.setDeleted(CommonConstant.INVALIDDELETE);
        dictDetailService.update(dictDetailEntity,new QueryWrapper<DictDetailEntity>().eq("dict_id",id));
    }
}
