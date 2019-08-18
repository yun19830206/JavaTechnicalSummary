package com.cloud.stagging.module.user.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CRM智能助手定时器集合
 * @author ChengYun
 * @date 2019/4/11  Vesion 1.0
 */
@Component
@Slf4j
public class CrmAssistantJob {

    @Scheduled(cron="0 0 2 * * ?")   //每天夜里1点点触发
    /**  CRON表达式    含义
     * "0/60 * *  * * ? " 每60秒执行一次,用于收集统计应用服务器信息
     * "0 0 12 * * ?"    每天中午十二点触发
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
    /** 根据拜访记录信息，更新CRM客户的更新时间 */
    public void updateCrmCustomerUpdateTime(){
        long startTime = System.currentTimeMillis();
        log.debug("定时任务【根据拜访记录信息，更新CRM客户的更新时间】 开始:{}",startTime);

        //crmBusinessService.updateCrmCustomerUpdateTime();

        log.debug("定时任务【根据拜访记录信息，更新CRM客户的更新时间】 结束:{} 共耗时:{}ms",startTime,(System.currentTimeMillis()-startTime));
    }

    @Scheduled(cron="0 0 9 * * ?")   //每天9点，统一给每一个销售人员，推送及时增加拜访记录
    /** 每天9点，统一给每一个销售人员，推送及时增加拜访记录 */
    public void pushAllSalesAddVisitInfo(){
        long startTime = System.currentTimeMillis();
        log.debug("定时任务【统一给每一个销售人员，推送及时增加拜访记录】 开始:{}",startTime);

        //crmPushMessageService.pushAllSalesAddVisitInfo();

        log.debug("定时任务【统一给每一个销售人员，推送及时增加拜访记录】 结束:{} 共耗时:{}ms",startTime,(System.currentTimeMillis()-startTime));
    }

}
