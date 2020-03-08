package com.cloud.stagging.pojo.knowledge;

import lombok.Data;

/**
 * 答案表的DO
 * @author ChengYun
 * @date 2020/3/7  Vesion 1.0
 */
@Data
public class SpAnswer {
    /** 自增主键ID */
    private Integer id;

    /** 答案内容 */
    private String answer;

    /** 答案归属问题ID */
    private Integer questionId;

}