package com.cloud.stagging.pojo.knowledge;

import com.cloud.stagging.enums.knowledge.ClassTypeEnum;
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

    /** 分类类型:QA_CLASS问题分类;MATERIAL_CLASS素材分类 */
    private ClassTypeEnum classType ;
}