package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.DictDetailEntity;

import java.util.List;
import java.util.Map;


/**
 * 字典表详情表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DictDetailService extends IService<DictDetailEntity> {


    /**
     *
     * @param params
     * @return
     */
    PageUtils queryList(Map<String,Object> params);

    List<DictDetailEntity> queryDictDetailsInfo(Map<String,Object> params);

    void deleteById(Long id);
}

