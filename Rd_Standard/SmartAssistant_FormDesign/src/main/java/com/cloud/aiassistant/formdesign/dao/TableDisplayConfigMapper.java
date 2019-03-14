package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.pojo.formdesign.TableDisplayConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表单设计器 表单查询结果展示字段Dao
 */
@Mapper
public interface TableDisplayConfigMapper {

    int insert(TableDisplayConfig record);

    TableDisplayConfig selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    List<TableDisplayConfig> selectByFormId(Long formid);
}