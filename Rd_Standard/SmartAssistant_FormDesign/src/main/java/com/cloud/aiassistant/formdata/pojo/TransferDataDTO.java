package com.cloud.aiassistant.formdata.pojo;

import lombok.Data;

/**
 * 表单数据转交给其他人DTO
 * @author ChengYun
 * @date 2019/4/30  Vesion 1.0
 */
@Data
public class TransferDataDTO {

    /** 转交数据表单配置ID */
    private Long tableId ;

    /** 转交数据表的表名 */
    private String tableName ;

    /** 转交表的数据ID */
    private Long dataId ;

    /** 转交用户ID */
    private Long toUserId ;

}
