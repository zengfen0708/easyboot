package com.zf.easyboot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zf.easyboot.common.constant.CommonConstant;
import com.zf.easyboot.common.utils.ConverterConstant;
import com.zf.easyboot.common.utils.PageUtils;
import com.zf.easyboot.modules.system.entity.JobEntity;
import com.zf.easyboot.modules.system.mapper.JobMapper;
import com.zf.easyboot.modules.system.service.DeptService;
import com.zf.easyboot.modules.system.service.JobService;
import com.zf.easyboot.modules.system.vo.JobVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service("jobService")
public class JobServiceImpl extends ServiceImpl<JobMapper, JobEntity> implements JobService {


    @Resource
    private DeptService deptService;

    /**
     * 查询岗位信息
     * @param params
     * @return
     */
    @Override
    public PageUtils queryList(Map<String, Object> params) {
        Integer currPage = ConverterConstant.converterInt.convert(params.get("page"));
        Integer pageSize = ConverterConstant.converterInt.convert(params.get("size"));
        currPage = Optional.ofNullable(currPage).orElse(CommonConstant.DEFAULT_PAGE);
        pageSize = Optional.ofNullable(pageSize).orElse(CommonConstant.DEFAULT_PAGE_SIZE);
        Integer startPage = currPage == 0 ? currPage * pageSize : (currPage - 1) * pageSize;

        List<JobVo> list = Optional.ofNullable(baseMapper.queryList(startPage, pageSize, params)).orElse(Lists.newArrayList());

        Map<Long,String> map=deptService.initDeptMapInfo();

        List<JobVo> result=list.stream().map(item->{
            long deptParentId=item.getDeptParentId();
            String deptName=item.getDeptName();

            if(Objects.equals(deptParentId,0L) || Objects.equals(deptParentId,1L)){
                return  item;
            }
            String parentName=map.get(deptParentId);
            if(StringUtils.isNotBlank(parentName)){
                deptName=parentName+"/"+deptName;
            }
            item.setDeptName(deptName);
            return  item;
        }).collect(Collectors.toList());

        Integer totalCount = baseMapper.queryListTotal(params);
        PageUtils page = new PageUtils(result, totalCount, pageSize, currPage);


        return page;
    }

    /**
     *   删除岗位信息
     * @param id
     */
    @Override
    public void deleteById(Long id) {

        JobEntity jobEntity=new JobEntity();
        jobEntity.setDeleted(CommonConstant.DELETED);
       int count= baseMapper.update(jobEntity,new QueryWrapper<JobEntity>().eq("id",id));

       log.info("修改岗位信息返回行数:{}",count);
    }
}
