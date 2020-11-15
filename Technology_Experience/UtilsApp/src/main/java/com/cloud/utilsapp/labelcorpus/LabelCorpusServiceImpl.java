package com.cloud.utilsapp.labelcorpus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 标签与语料 关系Service实现类
 * @author ChengYun
 * @date 2020/10/27  Vesion 1.0
 */
@Service
public class LabelCorpusServiceImpl implements ILabelCorpusService {

    private final LabelCorpusMapper labelCorpusDao ;

    public LabelCorpusServiceImpl(LabelCorpusMapper labelCorpusDao) {
        this.labelCorpusDao = labelCorpusDao;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public LabelCorpus createLabelCorpusRelation(LabelCorpus labelCorpus) {
        labelCorpusDao.insert(labelCorpus);
        return labelCorpus;
    }
}
