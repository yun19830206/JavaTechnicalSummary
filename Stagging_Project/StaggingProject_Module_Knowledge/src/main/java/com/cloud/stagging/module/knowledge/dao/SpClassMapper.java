package com.cloud.stagging.module.knowledge.dao;

import com.cloud.stagging.pojo.knowledge.SpClass;

public interface SpClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpClass record);

    int insertSelective(SpClass record);

    SpClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpClass record);

    int updateByPrimaryKey(SpClass record);
}