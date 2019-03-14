package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.formdesign.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 表单设计器 表单属性字段Dao
 */
@Mapper
public interface TableColumnConfigMapper {

    int insert(TableColumnConfig record);

    TableColumnConfig selectByPrimaryKey(Long id);

    List<TableColumnConfig> selectByFormId(Long formid);

}