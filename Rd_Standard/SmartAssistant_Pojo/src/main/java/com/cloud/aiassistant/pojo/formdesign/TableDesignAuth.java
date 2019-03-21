package com.cloud.aiassistant.pojo.formdesign;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 表单配置赋权 DO，也算是DTO使用(多一个userName属性)
 */
@Data
public class TableDesignAuth {

    /** 主键ID */
    private Long id;

    /** 表单设计ID，外键表单设计表 */
    private Long tableId;

    /** 权限付给对方ID */
    private Long userId;
    /** 多冗余一个属性，可以使得此DO当DTO使用 */
    private String userName;

    /** 创建人 */
    private Long createUser;

    /** 创建日期 */
    private Timestamp createTime;

    /** 外键租户ID */
    private Long tenantId;
}