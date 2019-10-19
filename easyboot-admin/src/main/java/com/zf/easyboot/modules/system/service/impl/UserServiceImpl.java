package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.mapper.UserMapper;
import com.zf.easyboot.modules.system.service.UserService;
import com.zf.easyboot.modules.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户系统界面
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    /**
     * 查询所有用户
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

        List<UserVo> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(list, totalCount, pageSize, currPage);

        return page;
    }
}
