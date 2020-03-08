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

    /** 问题类型:100=标准问题、200相似问法、300词条知识、400集合知识、500文档知识 */
    private Integer questionType;

}