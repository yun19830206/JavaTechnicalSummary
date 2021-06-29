package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpQuestion record);

    int insertSelective(SpQuestion record);

    SpQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpQuestion record);

    /** 根据主键更新问题属性  */
    int updateByPrimaryKey(SpQuestion question);

    /** 获得相似问法List */
    List<SpQuestion> getSimiQuestionList(Integer questionType);
}