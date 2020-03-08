package com.cloud.stagging.pojo.knowledge;

import lombok.Data;

/**
 * 分类表的DO
 * @author ChengYun
 * @date 2020/3/7  Vesion 1.0
 */
@Data
public class SpClass {
    /** 自增主键ID */
    private Integer id;

    /** 分类名称 */
    private String className;

}