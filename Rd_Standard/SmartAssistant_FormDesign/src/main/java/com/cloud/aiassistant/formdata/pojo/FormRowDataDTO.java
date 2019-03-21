package com.cloud.aiassistant.formdata.pojo;

import lombok.Data;

import java.util.List;

/**
 * 表单的一行数据，用于 增加一个表单的一行数据
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Data
public class FormRowDataDTO {

    /** 待查询 配置表的ID */
    private Long tableId ;

    /** 待查询 配置表名 */
    private String tableName ;

    /** 待查询的数据IDList */
    private List<OneColumnValue> columnValueList ;

}
