package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpQuestionLabel;

public interface SpQuestionLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpQuestionLabel record);

    int insertSelective(SpQuestionLabel record);

    SpQuestionLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpQuestionLabel record);

    int updateByPrimaryKey(SpQuestionLabel record);
}