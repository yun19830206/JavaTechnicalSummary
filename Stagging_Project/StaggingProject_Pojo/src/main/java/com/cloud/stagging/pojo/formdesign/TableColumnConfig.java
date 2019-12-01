package com.cloud.stagging.pojo.formdesign;


import com.cloud.stagging.enums.formdesign.TableColumnTypeEnum;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 表单字段属性DO
 * @author Cloud
 * @date 2019/3/7 Version 1
 */
@Data
public class TableColumnConfig {

    /** 创建表单的时候：每个表单默认增加 create_user、create_time、tenant_id3个 */
    public static final String DEFAULT_COLUMN_CREATE_USER = "create_user";
    public static final String DEFAULT_COLUMN_CREATE_TIME = "create_time";
    public static final String DEFAULT_COLUMN_UPDATE_TIME = "update_time";
    public static final String DEFAULT_COLUMN_TENANT_ID = "tenant_id";
    public static final String DEFAULT_COLUMN_DISPLAY_CUREATE_USER_NAME = "create_user_name";

    /** 主键ID */
    private Long id;

    /** 设计表ID，外键表单设计表 */
    private Long tableId;

    /** 本字段的序号，默认从1开始 */
    private Integer colSeq;

    /** 字段中文名 */
    private String chineseName;

    /** 字段英文名 */
    private String englishName;

    /** 字段类型(对应枚举TableColumnEnum:单行文本框、下拉框、富文本、多行文本框、日期、数字、手机号、邮箱号、附件、外键引用框) */
    private TableColumnTypeEnum colType;

    /** 字段长度 */
    private Integer colLength;

    /** 下拉框值域(拜访方法:电话拜访、上门拜访) */
    private String dropValue;

    /** 引用表值域(如:项目系统表 引用 客户表信息) */
    private Long fkValue;

    /** 默认值 */
    private String defaultValue;

    /** 是否引用展示字段(被表外键引用，展示字段)：1是展示，0不是 */
    private Integer displayColumn;

    /** 是否唯一：1必须唯一，0不做判断 */
    private Integer uniqued;

    /** 能否为空：1能为空，0不能为空 */
    private Integer empty;

    /** 创建人，外键用户表 */
    private Long createUser;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串)2019-03-07 19:19:38 */
    private Timestamp createTime;

    /** 租户ID */
    private Long tenantId;

}