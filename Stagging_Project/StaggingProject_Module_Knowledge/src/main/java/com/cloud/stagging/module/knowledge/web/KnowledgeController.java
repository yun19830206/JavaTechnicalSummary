package com.cloud.stagging.module.knowledge.web;

import com.cloud.stagging.module.knowledge.pojo.KnowledgeDTO;
import com.cloud.stagging.module.knowledge.service.IKnowledgeService;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识点Controller(包含整体问题和答案)
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
@Slf4j
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final IKnowledgeService knowledgeService ;

    @Autowired
    public KnowledgeController(IKnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    /**
     * 增加一个知识点(Ajax参数校验)
     * @param knowledgeDTO 一个知识点的DTO
     * @return AjaxResponse
     */
    @RequestMapping("/v1/knowledge/add")
    public AjaxResponse addKnowledge(@RequestBody @Validated KnowledgeDTO knowledgeDTO){
        knowledgeService.addKnowledge(knowledgeDTO);
        return AjaxResponse.success();
    }
}
