package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpLabel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpLabel record);

    int insertSelective(SpLabel record);

    SpLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpLabel record);

    int updateByPrimaryKey(SpLabel record);
}