package com.cloud.stagging.pojo.knowledge;

import lombok.Data;

/**
 * 问题表的DO
 * @author ChengYun
 * @date 2020/3/7  Vesion 1.0
 */
@Data
public class SpQuestion {
    /** 自增主键ID */
    private Integer id;

    /** 问题内容 */
    private String question;

    /** 问题归属分类ID */
    private Integer classId;

    /** 归属标准问题ID */
    private Integer standQuestionId;

    /** 问题类型:200=标准问题、201相似问法、202词条知识、203集合知识、204文档知识 */
    private Integer questionType;

}