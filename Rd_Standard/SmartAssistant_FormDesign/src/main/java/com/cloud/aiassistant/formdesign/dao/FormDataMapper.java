package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.formdesign.pojo.FormDataQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 表单数据  增删改查 Dao
 */
@Mapper
public interface FormDataMapper {

    /**
     * 查询某个表单的数据：包括我创建的 和 赋权给我的
     * @param formDataQueryDTO
     */
    List<Map<String,Object>> selectFormDataByFormDataQueryDTO(FormDataQueryDTO formDataQueryDTO);
}