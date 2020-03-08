package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpAnswer;

public interface SpAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpAnswer record);

    int insertSelective(SpAnswer record);

    SpAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpAnswer record);

    int updateByPrimaryKey(SpAnswer record);
}