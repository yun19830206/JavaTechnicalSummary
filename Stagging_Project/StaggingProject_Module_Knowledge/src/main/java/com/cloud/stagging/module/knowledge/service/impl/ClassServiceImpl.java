package com.cloud.stagging.module.knowledge.service.impl;

import com.cloud.stagging.module.knowledge.dao.SpClassMapper;
import com.cloud.stagging.module.knowledge.service.IClassService;
import com.cloud.stagging.pojo.knowledge.SpClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 知识分类Service
 * @author ChengYun
 * @date 2020/3/15  Vesion 1.0
 */
@Service
public class ClassServiceImpl implements IClassService {

    private final SpClassMapper classMapper;

    @Autowired
    public ClassServiceImpl(SpClassMapper classMapper) {
        this.classMapper = classMapper;
    }


    @Override
    public SpClass getOneClassById(Integer classId) {
        return classMapper.selectByPrimaryKey(classId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpClass create(SpClass spClass) {
        classMapper.insert(spClass);
        return spClass;
    }
}
