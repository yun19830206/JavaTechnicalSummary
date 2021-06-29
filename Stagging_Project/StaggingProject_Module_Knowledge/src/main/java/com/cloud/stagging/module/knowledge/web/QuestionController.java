package com.cloud.stagging.module.knowledge.web;

import com.cloud.stagging.module.knowledge.service.IQuestionService;
import com.cloud.stagging.module.knowledge.service.impl.QuestionServiceImpl;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题Controller
 * @author ChengYun
 * @date 2020/3/19  Vesion 1.0
 */
@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final IQuestionService questionService ;

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 获得相似问法List
     * @return 相似问法List
     */
    @RequestMapping("/v1/simiQuestion/list")
    public AjaxResponse getSimiQuestionList(){
        return AjaxResponse.success(questionService.getSimiQuestionList());
    }


}
