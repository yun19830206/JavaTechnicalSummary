package com.cloud.aiassistant.formdesign.dao;


import com.cloud.aiassistant.pojo.formdesign.TableDesignAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TableDesignAuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TableDesignAuth record);

    TableDesignAuth selectByPrimaryKey(Long id);

    /** 批量插入表单配置 授权数据 */
    void insertBatch(List<TableDesignAuth> tableDesignAuthList);

    /** 根据 表单配置表的主键ID，删除旧的授权数据 */
    void deleteByTableId(Long tableId);

    /** 获得当前表单配置，已经赋权的人员信息 */
    List<TableDesignAuth> selectByTableId(Long tableId);
}