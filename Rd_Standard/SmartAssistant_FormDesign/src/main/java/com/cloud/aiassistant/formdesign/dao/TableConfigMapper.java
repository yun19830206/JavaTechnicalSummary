package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.pojo.formdesign.TableConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表单设计器 表单Dao
 */
@Mapper
public interface TableConfigMapper {

    int insert(TableConfig record);

    TableConfig selectByPrimaryKey(Long id);

    /** 获得userId创建的表单配置数据 */
    List<TableConfig> selectByUserId(@Param("userId") Long id);

    /** 获得赋权给我的 表单配置数据 */
    List<TableConfig> selectAuthToMeTableConfig(@Param("autoToUserId") Long autoToUserId);

}