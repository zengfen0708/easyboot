package com.zf.easyboot.config.mybaitsplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import com.zf.easyboot.common.constant.CommonConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis通用字段填充
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/19.
 */
@Slf4j
@Component
public class MybatisCommonFieldHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("deleted",CommonConstant.DELETED,metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
