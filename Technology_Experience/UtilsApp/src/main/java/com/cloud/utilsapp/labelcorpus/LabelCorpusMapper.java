package com.cloud.utilsapp.labelcorpus;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 标签与语料 关系DAO
 */
@Mapper
public interface LabelCorpusMapper {

    /**
     * 增加 标签与语料 关系数据
     * @param labelCorpus LabelCorpus
     */
    void insert(LabelCorpus labelCorpus);
}