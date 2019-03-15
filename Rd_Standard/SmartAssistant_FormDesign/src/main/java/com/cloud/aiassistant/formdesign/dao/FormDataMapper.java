package com.cloud.aiassistant.formdesign.dao;

import com.cloud.aiassistant.formdesign.pojo.FormDataJudgeDuplicateQueryDTO;
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
    List<Map<String,Object>> selectMyFormDataAndAuthToMeData(FormDataQueryDTO formDataQueryDTO);

    /**
     * 根据FormDataJudgeDuplicateQueryDTO(表单ID，表名，字段名，值) 查数据
     * @param formDataJudgeDuplicateQueryDTO  FormDataJudgeDuplicateQueryDTO
     * @return
     */
    List<Map<String,Object>> selectByTableColumnValue(FormDataJudgeDuplicateQueryDTO formDataJudgeDuplicateQueryDTO);
}