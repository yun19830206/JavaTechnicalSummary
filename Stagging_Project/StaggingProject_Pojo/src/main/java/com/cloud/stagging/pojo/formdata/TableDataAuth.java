package com.cloud.stagging.pojo.formdata;

import com.cloud.stagging.enums.formdata.TableDataAuthTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 表单数据赋权 DO
 */
@Data
public class TableDataAuth {

    /** 主键ID */
    private Long id;

    /** 将谁的表付给别人 */
    private Long fromUser;

    /** 表单设计ID，外键表单设计表 */
    private Long fromTable;

    /**  权限付给对方ID*/
    private Long toUser;
    /**  权限付给对方姓名(冗余)*/
    private String toUserName;

    /** 赋权类型枚举：DATA_AUTH_TYPE_VIEW=查看权限,DATA_AUTH_TYPE_EDIT=修改权限,DATA_AUTH_TYPE_DEL=删除权限 */
    private TableDataAuthTypeEnum dataAuthType;

    /** 创建人 */
    private Long createUser;

    /** 创建日期 */
    private Date createTime;

    /** 外键租户ID */
    private Long tenantId;

}