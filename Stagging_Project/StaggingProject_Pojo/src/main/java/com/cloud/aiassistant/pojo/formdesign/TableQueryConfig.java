package com.cloud.aiassistant.pojo.formdesign;

import com.cloud.aiassistant.enums.formdesign.TableQueryConditionEnum;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 表单设计 表单查询DO
 * @author Cloud
 * @date 2019/3/7 Version 1
 */
@Data
public class TableQueryConfig {
    /** 主键ID */
    private Long id;

    /** 设计表ID，外键表单设计表 */
    private Long tableId;

    /** 展示序号 */
    private Integer querySeq;

    /** 外键到表单字段设计表主键 */
    private Long tableColumn;
    private TableColumnConfig tableColumnConfig;

    /** 查询条件类型(like,=,>,<,<>) */
    private TableQueryConditionEnum queryType;

    /** 创建人，外键用户表 */
    private Long createUser;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串)2019-03-07 19:19:38 */
    private Timestamp createTime;

    /** 租户ID */
    private Long tenantId;

}