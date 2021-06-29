package com.cloud.stagging.job.modules.knowledge;

import com.cloud.stagging.core.security.config.JWTConfig;
import com.cloud.stagging.module.knowledge.pojo.KnowledgeVO;
import com.cloud.stagging.module.knowledge.service.IKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 问答知识库定时任务：比对知识点时间的相似度; 知识点推荐数据生成
 * @author ChengYun
 * @date 2020/4/9  Vesion 1.0
 */
@Component
@Slf4j
public class KnowledgeJob {

    private final IKnowledgeService knowledgeService;

    @Autowired
    public KnowledgeJob(IKnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    /**  CRON表达式    含义
     "0/60 * *  * * ? " 每60秒执行一次,用于收集统计应用服务器信息
     "0 0 12 * * ?"    每天中午十二点触发
     "0 15 10 ? * *"    每天早上10：15触发
     "0 15 10 * * ?"    每天早上10：15触发
     "0 15 10 * * ? *"    每天早上10：15触发
     "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
     */
    /**
     * 知识点推荐数据生成 定时任务,每天夜里2点触发
     */
    @Scheduled(cron="0/60 * *  * * ? ")
    //@Scheduled(cron="0 0 2 * * ?")
    public void generateSuggestQuestion(){
        long startTime = System.currentTimeMillis();
        log.debug("定时任务【知识点推荐数据生成】 开始:{}",startTime);

        knowledgeService.generateSuggestQuestion();

        log.debug("定时任务【知识点推荐数据生成】 结束:{} 共耗时:{}ms",System.currentTimeMillis(),(System.currentTimeMillis()-startTime));
    }

}
