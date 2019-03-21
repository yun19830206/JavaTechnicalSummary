package com.cloud.aiassistant.formdata.dao;


import com.cloud.aiassistant.pojo.formdata.TableDataAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TableDataAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableDataAuth record);

    TableDataAuth selectByPrimaryKey(Integer id);

    /** 删除当前人的，当前表单的  授权数据 */
    void deleteByUserIdAndTableId(@Param("fromUser") Long fromUser, @Param("fromTable") Long fromTable);

    /** 批量插入当前表单数据的授权数据 */
    void insertBatch(List<TableDataAuth> tableDataAuthList);

    /** 获得我的表单数据 赋权 信息 */
    List<TableDataAuth> selectByUserIdAndTableId(@Param("fromUser") Long fromUser, @Param("fromTable") Long tableId);
}