package com.learn.ejtask.mapper;

import com.learn.ejtask.model.ToolEmailConfig;
import org.springframework.stereotype.Component;

@Component
public interface ToolEmailConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(ToolEmailConfig record);

    int insertSelective(ToolEmailConfig record);

    ToolEmailConfig selectByPrimaryKey(Long configId);

    int updateByPrimaryKeySelective(ToolEmailConfig record);

    int updateByPrimaryKey(ToolEmailConfig record);
}