package com.learn.ejtask.mapper;


import com.learn.ejtask.model.SysQuartzJob;
import com.learn.ejtask.model.dto.JobDto;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysQuartzJobMapper {

    int deleteByPrimaryKey(Long jobId);

    int insert(SysQuartzJob record);

    int insertSelective(SysQuartzJob record);

    SysQuartzJob selectByPrimaryKey(Long jobId);

    SysQuartzJobExt selectById(@Param("jobId") Long jobId);

    List<SysQuartzJob> getJobListByItem(@Param("jobDto") JobDto jobDto);

    List<SysQuartzJobExt> getJobExtListByItem(@Param("jobDto") JobDto jobDto);

    int updateByPrimaryKeySelective(SysQuartzJob record);

    int updateByPrimaryKey(SysQuartzJob record);
}