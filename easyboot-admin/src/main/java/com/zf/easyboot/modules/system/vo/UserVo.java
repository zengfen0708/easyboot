package com.zf.easyboot.modules.system.vo;

import com.zf.easyboot.modules.system.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 疯信子
 * @version 1.0
 * @date 2019/10/12.
 */
@Data
public class UserVo extends UserEntity {

    private String deptName;
    private String jobName;

    private List<Long> roles;
}
