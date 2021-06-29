package com.cloud.stagging.module.knowledge.service;

import com.cloud.stagging.pojo.knowledge.SpQuestion;

import java.util.List;

/**
 * 问题Service
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
public interface IQuestionService {

    /**
     * 获得相似问法List
     * @return 相似问法List
     */
    List<SpQuestion> getSimiQuestionList();
}
