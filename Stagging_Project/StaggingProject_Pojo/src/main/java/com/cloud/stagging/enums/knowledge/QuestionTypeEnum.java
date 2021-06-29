package com.cloud.stagging.enums.knowledge;

/**
 * 问题类型(sp_question.question_type):200=标准问题、201相似问法、202词条知识、203集合知识、204文档知识
 * @author ChengYun
 * @date 2020/3/18  Vesion 1.0
 */
public enum QuestionTypeEnum {
    STAND_QUESTION(200,"STAND_QUESTION","标准问题"),
    SIMI_QUESTION(201,"SIMI_QUESTION","相似问法"),
    ENTITY_KNOWLEDGE(202,"ENTITY_KNOWLEDGE","词条知识"),
    COLLECT_KNOWLEDGE(203,"COLLECT_KNOWLEDGE","集合知识"),
    DOCUMENT_QUESTION(204,"DOCUMENT_QUESTION","文档知识"),
    ;

    /** 问题类型ID */
    private Integer questionType ;
    /** 问题类型英文描述 */
    private String questionTypeEnDesc;
    /** 问题类型中文描述 */
    private String questionTypeChnDesc;

    QuestionTypeEnum(Integer questionType, String questionTypeEnDesc, String questionTypeChnDesc){
        this.questionType = questionType ;
        this.questionTypeEnDesc = questionTypeEnDesc ;
        this.questionTypeChnDesc = questionTypeChnDesc ;
    }

    public Integer getQuestionType(){
        return questionType;
    }
}
