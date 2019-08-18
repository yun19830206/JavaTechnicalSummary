package com.cloud.aiassistant.enums.formdesign;

/**
 * 表单设计器 字段类型枚举:单行文本框、下拉框、富文本、多行文本框、日期、数字、手机号、邮箱号、附件
 * @author ChengYun
 * @date 2019/3/7 Version 1
 */
public enum TableColumnTypeEnum {

    /**单行文本框*/
    COLUMN_SIGN_LINE_TEXT(100,"sign line text","单行文本框"),
    /** 下拉框 */
    COLUMN_DROP_BOX(101,"drop box","下拉框"),
    /** 富文本 */
    COLUMN_RICH_TEXT(102,"rich text","富文本"),
    /** 多行文本框 */
    COLUMN_MANY_LINE_TEXT(103,"many line text","多行文本框"),
    /** 日期 */
    COLUMN_DATE_TIME(104,"date time","日期"),
    /** 数字 */
    COLUMN_NUMBER(105,"number","数字"),
    /** 手机号 */
    COLUMN_PHONE_NUMBER(106,"phone_number","手机号"),
    /** 邮箱号 */
    COLUMN_EMAIL(107,"email","邮箱号"),
    /** 附件 */
    COLUMN_FILE(108,"file","附件"),
    /** 外键引用框 */
    COLUMN_FOREIGN_KEY(109,"foreign key","外键引用框"),
    ;

    /** 表单设计器字段类型唯一标识 */
    private Integer columnType;
    /** 表单设计器字段类型英文描述 */
    private String columnEnglishDesc ;
    /** 表单设计器字段类型中文描述 */
    private String columnChineseDesc ;

    TableColumnTypeEnum(Integer columnType, String columnEnglishDesc, String columnChineseDesc) {
        this.columnType = columnType;
        this.columnEnglishDesc = columnEnglishDesc;
        this.columnChineseDesc = columnChineseDesc;
    }

    public Integer getColumnType() {
        return columnType;
    }

    public String getColumnEnglishDesc() {
        return columnEnglishDesc;
    }

    public String getColumnChineseDesc() {
        return columnChineseDesc;
    }
}
