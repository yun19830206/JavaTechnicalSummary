package com.cloud.stagging.pojo.knowledge;

import lombok.Data;

/**
 * 标签表的DO
 * @author ChengYun
 * @date 2020/3/7  Vesion 1.0
 */
@Data
public class SpLabel {
    /** 自增主键ID */
    private Integer id;

    /** 标签名称 */
    private String labelName;

}