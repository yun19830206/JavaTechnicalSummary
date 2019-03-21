package com.cloud.aiassistant.formdata.pojo;

import lombok.Data;

/**
 * 判断表达字段是否重复查询的DTO
 * @author ChengYun
 * @date 2019/3/15  Vesion 1.0
 */
@Data
public class FormDataJudgeDuplicateQueryDTO {

    /** 待查询 配置表的ID */
    private Long tableId ;

    /** 待查询 配置表名 */
    private String tableName ;

    /** 判断重复的字段名 */
    private String columnName ;

    /** 判断重复的value */
    private String columnValue ;

}
