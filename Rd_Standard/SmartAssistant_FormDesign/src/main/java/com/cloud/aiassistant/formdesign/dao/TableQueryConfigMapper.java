package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.pojo.formdesign.TableQueryConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表单设计器 表单查询条件Dao
 */
@Mapper
public interface TableQueryConfigMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TableQueryConfig record);

    TableQueryConfig selectByPrimaryKey(Long id);

    List<TableQueryConfig> selectByFormId(Long formid);
}