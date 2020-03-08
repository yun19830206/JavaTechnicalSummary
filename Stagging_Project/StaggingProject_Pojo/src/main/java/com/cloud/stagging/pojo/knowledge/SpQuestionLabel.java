package com.cloud.stagging.pojo.knowledge;

import lombok.Data;

/**
 * 问题标签关系表的DO
 * @author ChengYun
 * @date 2020/3/7  Vesion 1.0
 */
@Data
public class SpQuestionLabel {
    /**  自增主键ID */
    private Integer id;

    /** 标签ID */
    private Integer labelId;

    /** 问题ID */
    private Integer questionId;
}