package com.cloud.stagging.pojo.file;

import lombok.Data;

import java.util.Date;

/**
 * 文件DO
 */
@Data
public class PublicFile {

    /** 主键ID */
    private Long id;

    /** 原始文件名 */
    private String fileNameOriginal;

    /** 系统中存储的唯一文件名 */
    private String fileNameNew;

    /** 相对upload文件夹相对路径 */
    private String relativePath;

    /** 操作系统绝对路径 */
    private String absolutePath;

    /** 对应系统URL */
    private String url;

    /** 文件类型 */
    private String contentType;

    /** 创建人，外键用户表 */
    private Long createUser;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串) */
    private Date createTime;

    /** 所属租户ID */
    private Long tenantId;

    /** del=1为删除，del=0为正常 */
    private Integer del;
}