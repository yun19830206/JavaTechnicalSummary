package com.cloud.aiassistant.file.dao;

import com.cloud.aiassistant.pojo.file.PublicFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文件操作Dao
 */
@Mapper
public interface FileMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PublicFile record);

    PublicFile selectByPrimaryKey(Long id);

    void updateUrl(@Param("id") Long id, @Param("url") String url);
}