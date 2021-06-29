package com.cloud.stagging.module.knowledge.service;

import com.cloud.stagging.module.knowledge.pojo.KnowledgeDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 知识点的Service
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
public interface IKnowledgeService {

    /**
     * 增加一个知识点：问题和多个答案
     * @param knowledgeDTO 一个知识点的DTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    void addKnowledge(KnowledgeDTO knowledgeDTO);

    /**
     * 知识点推荐数据生成
     */
    @Transactional(rollbackFor = RuntimeException.class)
    void generateSuggestQuestion();
}
