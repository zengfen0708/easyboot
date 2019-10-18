package com.zf.easyboot.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.easyboot.modules.system.entity.DeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 部门表
 *
 * @author 疯信子
 * @email zengms0708@gmail.com
 * @date 2019-10-12 23:02:34
 */
public interface DeptMapper extends BaseMapper<DeptEntity> {

    List<DeptEntity> queryList(@Param("params") Map<String, Object> params);

    Integer queryListTotal(@Param("params") Map<String, Object> params);


    /**
     * 修改部门表
     *
     * @param id
     * @return
     */
    int updateDeptId(@Param("ids") String  id);



    /**
     * 查询对应下级节点信息
     * @param id
     * @return
     */
    List<Long>  queryByDeptIds(@Param("id") Long id);


    /**
     * 查询下一级的数据
     * @param id
     * @return
     */
    List<Long> querySonDeptIds(@Param("ids")String id);

}
