package com.cloud.aiassistant.formdata.pojo;

import lombok.Data;

/**
 * 一个字段属性值DTO
 * @author ChengYun
 * @date 2019/3/15  Vesion 1.0
 */
@Data
public class OneColumnValue {

    /**  属性名 */
    private String columnName;

    /**  属性值 */
    private String columnValue;

    public OneColumnValue(){}

    public OneColumnValue(String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }
}
