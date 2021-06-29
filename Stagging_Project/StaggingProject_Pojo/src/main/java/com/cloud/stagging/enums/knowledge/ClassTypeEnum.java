package com.cloud.stagging.enums.knowledge;

/**
 * 分类类型枚举(SpClass.classType) 用于模拟物理模型为字符串的枚举
 * @author ChengYun
 * @date 2020/3/14  Vesion 1.0
 */
public enum ClassTypeEnum {

    /** QA_CLASS 问题分类 */
    QA_CLASS(100,"QA_CLASS","问题分类"),

    /** MATERIAL_CLASS 素材分类 */
    MATERIAL_CLASS(101,"MATERIAL_CLASS","素材分类"),;

    /** 分类类型ID */
    private Integer classType;
    /** 分类类型英文描述 */
    private String classEnDesc;
    /** 分类类型中文描述 */
    private String classChDesc ;

    ClassTypeEnum(Integer classType, String classEnDesc, String classChDesc) {
        this.classType = classType;
        this.classEnDesc = classEnDesc;
        this.classChDesc = classChDesc;
    }


    public Integer getClassType() {
        return classType;
    }

    public String getClassEnDesc() {
        return classEnDesc;
    }

    public String getClassChDesc() {
        return classChDesc;
    }
}
