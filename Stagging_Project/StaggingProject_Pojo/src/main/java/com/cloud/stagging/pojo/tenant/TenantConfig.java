package com.cloud.stagging.pojo.tenant;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 多租户管理配置DO
 * @author Cloud
 * @date 2018/12/23 Version 1
 */
@Data
public class TenantConfig {

    /** 数据库主键ID */
    private Long id;

    /** 租户的唯一标识：租户对外唯一标识:时间戳+8位随机数; 100默认sys租户,200默认预留,300默认预留, */
    private String tenantNumber;

    /** 租户名称 */
    private String tenantName;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串)2019-03-07 19:19:38 */
    private Timestamp createTime ;

    /** 创建用户 */
    private Long createUser ;

}