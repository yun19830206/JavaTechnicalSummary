package com.cloud.stagging.module.knowledge.pojo;

import com.cloud.stagging.pojo.knowledge.SpAnswer;
import com.cloud.stagging.pojo.knowledge.SpClass;
import com.cloud.stagging.pojo.knowledge.SpLabel;
import com.cloud.stagging.pojo.knowledge.SpQuestion;
import lombok.Data;

import java.util.List;

/**
 * 一条知识业务对象
 * @author ChengYun
 * @date 2020/3/8  Vesion 1.0
 */
@Data
public class KnowledgeVO {

    /** 知识点ID */
    private Integer knowledgeId ;

    /** 知识归属分析DO */
    private SpClass classDO ;

    /** 知识点的问题 */
    private SpQuestion standQuestion;

    /** 知识点的相似问法 */
    private List<SpQuestion> similarQuestion;

    /** 知识点的答案(多答案模式) */
    private List<SpAnswer> answerList;

    /** 知识点的标签(会有多个标签) */
    private List<SpLabel> labelList;
}
