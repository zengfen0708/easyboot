package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;
import com.zf.easyboot.modules.system.mapper.DictDetailMapper;
import com.zf.easyboot.modules.system.service.DictDetailService;
import com.zf.easyboot.modules.system.vo.DictDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service("dictDetailService")
public class DictDetailServiceImpl extends ServiceImpl<DictDetailMapper, DictDetailEntity> implements DictDetailService {


    /**
     * 根据字典标识查询对应的详情
     *
     * @param params
     * @return
     */
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

        List<DictDetailEntity> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(list, totalCount, pageSize, currPage);


        return page;
    }

    @Override
    public List<DictDetailVo> queryDictDetailsInfo(Map<String, Object> params) {
        List<DictDetailVo> data = baseMapper.queryDictDetailsInfo(params);

        return data;
    }

    @Override
    public void deleteById(Long id) {
        int count = baseMapper.updateDetailId(id);
        log.info("返回修改行数:{}", count);

    }
}
