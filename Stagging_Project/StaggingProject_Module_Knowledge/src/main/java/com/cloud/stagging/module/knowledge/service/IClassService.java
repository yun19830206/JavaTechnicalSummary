package com.cloud.stagging.module.knowledge.service;

import com.cloud.stagging.pojo.knowledge.SpClass;
import org.springframework.transaction.annotation.Transactional;

/**
 * 知识分类Service
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
public interface IClassService {

    /**
     * 根据分类ID,获得分类详情DO
     * @param classId 分类主键ID
     * @return 分类详情DO
     */
    SpClass getOneClassById(Integer classId);

    /**
     * 新建分类
     * @param spClass 分类DO
     * @return 分类详情DO
     */
    @Transactional(rollbackFor = Exception.class)
    SpClass create(SpClass spClass);
}
