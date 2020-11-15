package com.cloud.utilsapp.labelcorpus;

import lombok.Data;

import java.util.Date;

/**
 * 标签与语料 关系表DO(up_label_corpus)
 * @author ChengYun
 * @date 2020/10/27  Vesion 1.0
 */
@Data
public class LabelCorpus {

    /** 主键ID */
    private Integer id;

    /** 标签名称 */
    private String labelName;

    /** 标签对应的语料 */
    private String corpus;

    /** 时间 */
    private Date createTime;

}