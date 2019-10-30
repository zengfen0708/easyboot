package com.zf.easyboot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
public interface UserService extends IService<UserEntity> {
    PageUtils queryList(Map<String,Object> params);


    void handleUserInfo(UserVo userVo);

    void delete(Long id);

    ApiMessage importExcelData(String filePath);

    List<UserEntity> exportExcel(Map<String, Object> params);
}
