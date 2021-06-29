package com.cloud.stagging.module.knowledge.service.impl;

import com.cloud.stagging.core.exception.ParameterValidatorException;
import com.cloud.stagging.enums.knowledge.QuestionTypeEnum;
import com.cloud.stagging.module.knowledge.pojo.KnowledgeDTO;
import com.cloud.stagging.module.knowledge.service.IKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cloud.stagging.core.utils.ValidatorUtils.validatorIntEnum;
import static com.cloud.stagging.core.utils.ValidatorUtils.validatorStringLength;

/**
 * 知识点的Service
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
@Service
@Slf4j
public class KnowledgeServiceImpl implements IKnowledgeService {

    @Override
    public void addKnowledge(KnowledgeDTO knowledgeDTO) {
        //1:进行额外的参数校验
        validatorKnowledge(knowledgeDTO);
    }

    @Override
    public void generateSuggestQuestion() {
        //随机抛出异常，用于示例验证 即使有异常，定时任务后续还可以正常进行。
        if(System.currentTimeMillis() % 2 == 0 ){
            throw new RuntimeException("知识点推荐数据生成 业务逻辑执行出错，抛出异常。");
        }
        log.debug("知识点推荐数据生成 业务逻辑代码");
    }

    /**
     * 验证一个知识点KnowledgeDTO是否合法，合法直接过，不合法由验证工具抛出异常，业务Server无需捕捉处理异常
     * @param knowledgeDTO 知识点DTO
     */
    private void validatorKnowledge(KnowledgeDTO knowledgeDTO) {
        if(null == knowledgeDTO){
            throw new ParameterValidatorException("请求参数合集检测失败，请按必填参数填写。");
        }

        //1:默认hibernate validator验证已经通过

        //2:验证KnowledgeDTO的问题类型属性questionType枚举是否合法。
        List<Integer> questionTypeEnumInteger = new ArrayList<>();
        QuestionTypeEnum[] questionTypeEnums = QuestionTypeEnum.values();
        for (QuestionTypeEnum questionTypeEnum : questionTypeEnums) {
            questionTypeEnumInteger.add(questionTypeEnum.getQuestionType());
        }
        validatorIntEnum(knowledgeDTO.getQuestionType(),questionTypeEnumInteger,"问题类型必须是枚举值：200=标准问题、201相似问法、202词条知识、203集合知识、204文档知识");

        //3:示例验证字符串长度是否合规
        validatorStringLength(knowledgeDTO.getQuestion(),4, 20, "知识点问题长度必须介于4和20之间");
    }
}
