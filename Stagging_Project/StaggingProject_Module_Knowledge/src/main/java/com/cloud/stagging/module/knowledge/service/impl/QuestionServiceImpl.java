package com.cloud.stagging.module.knowledge.service.impl;

import com.cloud.stagging.enums.knowledge.QuestionTypeEnum;
import com.cloud.stagging.module.knowledge.dao.SpQuestionMapper;
import com.cloud.stagging.module.knowledge.service.IQuestionService;
import com.cloud.stagging.pojo.knowledge.SpQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 问题Service
 * @author ChengYun
 * @date 2020/3/19  Vesion 1.0
 */
@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {

    private final SpQuestionMapper questionDao ;

    @Autowired
    public QuestionServiceImpl(SpQuestionMapper questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<SpQuestion> getSimiQuestionList() {
        return questionDao.getSimiQuestionList(QuestionTypeEnum.SIMI_QUESTION.getQuestionType());
    }
}
