package com.cloud.utilsapp.labelcorpus;

/**
 * 标签与语料 关系Service接口
 * @author ChengYun
 * @date 2020/10/27  Vesion 1.0
 */
public interface ILabelCorpusService {

    /**
     * 创建一条 标签与语料 关系数据
     * @param labelCorpus 标签与语料 关系数据
     */
    LabelCorpus createLabelCorpusRelation(LabelCorpus labelCorpus);
}
