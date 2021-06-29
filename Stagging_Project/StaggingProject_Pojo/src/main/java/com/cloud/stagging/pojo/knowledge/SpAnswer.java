package com.cloud.stagging.pojo.knowledge;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 2, max = 200, message = "知识点答案长度必须介于2和200之间")
    private String answer;

    /** 答案归属问题ID */
    private Integer questionId;

}