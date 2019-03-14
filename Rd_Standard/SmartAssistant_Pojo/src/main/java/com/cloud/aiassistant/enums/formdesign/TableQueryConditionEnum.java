package com.cloud.aiassistant.enums.formdesign;

/**
 * 表单设计器中  查询条件类型枚举(like,=,>,<,<>)
 * @author ChengYun
 * @date $time $date Vesion 1.0 Version 1
 */
public enum TableQueryConditionEnum {

    /**包含关系*/
    CONDITION_ENUM_LIKE(200,"like","包含关系"),
    /**等于*/
    CONDITION_ENUM_EQUAL(201,"equal","等于"),
    /**大于*/
    CONDITION_ENUM_MORE(202,"more","大于"),
    /**小于*/
    CONDITION_ENUM_LESS(203,"less","小于"),
    /**介于之间*/
    CONDITION_ENUM_BETWEEN(204,"between","介于之间"),
    ;

    /** 表单设计器 查询条件类型唯一标识 */
    private Integer queryType;
    /** 表单设计器 查询条件英文描述 */
    private String queryEnglishDesc ;
    /** 表单设计器 查询条件中文描述 */
    private String queryChineseDesc ;

    TableQueryConditionEnum(Integer queryType, String queryEnglishDesc, String queryChineseDesc) {
        this.queryType = queryType;
        this.queryEnglishDesc = queryEnglishDesc;
        this.queryChineseDesc = queryChineseDesc;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public String getQueryEnglishDesc() {
        return queryEnglishDesc;
    }

    public String getQueryChineseDesc() {
        return queryChineseDesc;
    }
}
