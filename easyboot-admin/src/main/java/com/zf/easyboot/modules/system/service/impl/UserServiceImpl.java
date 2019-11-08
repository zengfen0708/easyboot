package com.zf.easyboot.modules.system.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.enums.HttpStatus;
import com.zf.easyboot.common.utils.ApiMessage;
import com.zf.easyboot.common.utils.BeanCopierUtils;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.UserEntity;
import com.zf.easyboot.modules.system.entity.UserRoleEntity;
import com.zf.easyboot.modules.system.excel.UserExcelVo;
import com.zf.easyboot.modules.system.mapper.UserMapper;
import com.zf.easyboot.modules.system.service.UserRoleService;
import com.zf.easyboot.modules.system.service.UserService;
import com.zf.easyboot.modules.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Autowired
    private UserRoleService userRoleService;


    /**
     * 查询所有用户
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryList(Map<String, Object> params) {
        Integer currPage = ConverterConstant.converterPageInfo.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterPageInfo.convert(params.get("size"));
        //当前查询页码
        currPage = Optional.ofNullable(currPage).orElse(CommonConstant.DEFAULT_PAGE);
        //分页大小
        pageSize = Optional.ofNullable(pageSize).orElse(CommonConstant.DEFAULT_PAGE_SIZE);

        Integer startPage = currPage == 0 ? currPage * pageSize : (currPage - 1) * pageSize;

        List<UserVo> list = baseMapper.queryList(startPage, pageSize, params);
        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(list, totalCount, pageSize, currPage);

        return page;
    }


    /**
     * 保存或者修改用户信息
     * @param userVo
     */
    @Override
    @Transactional
    public void handleUserInfo(UserVo userVo) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //修改sys_user
        UserEntity userEntity = new UserEntity();
        BeanCopierUtils.copyProperties(userVo, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        Long userId=userEntity.getId();
        if(userId==null){
            baseMapper.insert(userEntity);
        }else {
            baseMapper.updateById(userEntity);
        }
        //先删除sys_user_role
       final Long id = userEntity.getId();

        userRoleService.deleteByUserId(id);
        //添加新的数据到sys_user_role
        List<Long> roleIds = userVo.getRoles();
        //重新添加用户信息
        List<UserRoleEntity> list = roleIds.parallelStream().map(item -> buildUserRole(item, id))
                .collect(Collectors.toList());

        if (!CollectionUtil.isEmpty(list)) {
            userRoleService.saveBatch(list);
        }

    }

    /**
     * 初始化角色和用户信息
     * @param roleId
     * @param userId
     * @return
     */
    private UserRoleEntity buildUserRole(Long roleId, Long userId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleId);
        userRoleEntity.setUserId(userId);
        return userRoleEntity;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        UserEntity userEntity = new UserEntity();
        userEntity.setDeleted(CommonConstant.INVALIDDELETE);
        baseMapper.update(userEntity, new QueryWrapper<UserEntity>().eq("id", id));
    }

    @Override
    public ApiMessage importExcelData(String filePath) {
        ImportParams params = new ImportParams();
        params.setHeadRows(CommonConstant.EXCELHEADROWS);//设置读取的开始行

        //读取excel文件
        List<UserExcelVo> excelList = ExcelImportUtil.importExcel(
                new File(filePath), UserExcelVo.class, params);

        if (!CollectionUtils.isEmpty(excelList)) {
            // 多线程问题
            List<UserEntity> list = excelList.parallelStream()
                    .map(item -> initUserEntiry(item)).collect(Collectors.toList());
            super.saveBatch(list, CommonConstant.SQLBATCHNUM);

            return ApiMessage.ofSuccess();
        }

        return ApiMessage.putHttpStatus(HttpStatus.FILE_IMPORT_ERROR);
    }


    /**
     * 导出excel数据
     *
     * @param params
     * @return
     */
    @Override
    public List<UserEntity> exportExcel(Map<String, Object> params) {

        return baseMapper.exportExcel(params);
    }


    /**
     * 初始化数据
     *
     * @param userExcelEntity
     * @return
     */
    private UserEntity initUserEntiry(UserExcelVo userExcelEntity) {
        UserEntity userEntity = new UserEntity();
        BeanCopierUtils.copyProperties(userExcelEntity, userEntity);
        return userEntity;
    }
}
