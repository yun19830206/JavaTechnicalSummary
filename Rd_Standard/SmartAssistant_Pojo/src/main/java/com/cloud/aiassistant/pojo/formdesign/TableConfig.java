package com.cloud.aiassistant.pojo.formdesign;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 表单设计DO
 * @author Cloud
 * @date 2019/3/7 Version 1
 */
@Data
public class TableConfig {

    /** 主键ID */
    private Long id;

    /** 表格中文名称 */
    private String chineseName;

    /** 表格英文名称 */
    private String englishName;

    /** 创建用户 */
    private Long createUser;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串)2019-03-07 19:19:38 */
    private Timestamp createTime;

    /** 租户ID */
    private Long tenantId;


}