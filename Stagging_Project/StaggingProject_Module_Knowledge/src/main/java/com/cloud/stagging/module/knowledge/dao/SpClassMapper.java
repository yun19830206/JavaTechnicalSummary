package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpClass;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类Class DAO
 */
@Mapper
public interface SpClassMapper {

    int insert(SpClass spClass);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpClass spClass);

    int updateByPrimaryKey(SpClass spClass);

    SpClass selectByPrimaryKey(Integer id);

}