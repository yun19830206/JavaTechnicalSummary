package com.cloud.stagging.enums.formdata;

/**
 * 表单数据赋权类型枚举
 * @author ChengYun
 * @date 2019/3/18  Vesion 1.0
 */
public enum TableDataAuthTypeEnum {

    /** 数据查看权限 */
    DATA_AUTH_TYPE_VIEW(300,"view","数据查看权限"),
    /** 数据编辑权限 */
    DATA_AUTH_TYPE_EDIT(301,"edit","数据编辑权限"),
    /** 数据删除权限 */
    DATA_AUTH_TYPE_DEL(302,"delete","数据删除权限"),
    ;

    /** 表单设计器 查询条件类型唯一标识 */
    private Integer queryType;
    /** 表单设计器 查询条件英文描述 */
    private String queryEnglishDesc ;
    /** 表单设计器 查询条件中文描述 */
    private String queryChineseDesc ;

    TableDataAuthTypeEnum(Integer queryType, String queryEnglishDesc, String queryChineseDesc) {
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
