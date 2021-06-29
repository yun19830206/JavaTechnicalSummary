package com.cloud.stagging.module.knowledge.pojo;

import lombok.Data;

/**
 * 知识点查询DTO
 * @author ChengYun
 * @date 2020/3/8  Vesion 1.0
 */
@Data
public class KnowledgeQueryDTO {

    /** 知识点ID */
    private Integer knlowledgeId ;

    /** 分类ID */
    private Integer classID ;

}
