package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpQuestion;

public interface SpQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpQuestion record);

    int insertSelective(SpQuestion record);

    SpQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpQuestion record);

    int updateByPrimaryKey(SpQuestion record);
}