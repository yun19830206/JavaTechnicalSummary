package com.cloud.stagging.module.knowledge.pojo;

import com.cloud.stagging.pojo.knowledge.SpAnswer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 一个知识点的DTO，包含参数校验部分示例代码
 * @author ChengYun
 * @date 2020/4/7  Vesion 1.0
 */
@Data
public class KnowledgeDTO {
/*
     Bean Validation 中内置的 constraint
         @Null   被注释的元素必须为 null
         @NotNull    被注释的元素必须不为 null
         @AssertTrue     被注释的元素必须为 true
         @AssertFalse    被注释的元素必须为 false
         @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
         @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
         @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
         @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
         @Size(max=, min=)   被注释的元素的大小必须在指定的范围内：不能验证Integer，适用于String, Collection, Map and arrays
         @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
         @Past   被注释的元素必须是一个过去的日期
         @Future     被注释的元素必须是一个将来的日期
         @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
     Hibernate Validator 附加的 constraint
         @NotBlank(message =)   验证字符串非null，且长度必须大于0
         @Email  被注释的元素必须是电子邮箱地址
         @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
         @NotEmpty   被注释的字符串的必须非空
         @Range(min=,max=,message=)  被注释的元素必须在合适的范围内：验证int类型
  */

    /** 知识点ID */
    private Integer knowledgeId ;

    /** 问题内容 */
    @Length(min = 4, max = 20, message = "知识点问题长度必须介于4和20之间")
    private String question;

    /** 问题归属分类ID */
    @NotNull(message = "知识点归属分类不能为空")
    private Integer classId;

    /** 归属标准问题ID */
    private Integer standQuestionId;

    /** 问题类型:200=标准问题、201相似问法、202词条知识、203集合知识、204文档知识 */
    @Range(min = 200, max = 299, message = "问题类型为枚举:200=标准问题、201相似问法、202词条知识、203集合知识、204文档知识")
    private Integer questionType;

    //@Valid用在属性用，标识处理内联属性验证
    /** 本知识点的答案List */
    @Valid
    private List<SpAnswer> answersList ;


}
