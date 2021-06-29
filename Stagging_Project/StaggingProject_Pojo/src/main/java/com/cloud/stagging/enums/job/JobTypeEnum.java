package com.cloud.stagging.enums.job;

/**
 * 定时任务枚举
 * @author ChengYun
 * @date 2020/5/28  Vesion 1.0
 */
public enum JobTypeEnum {

    /** 获得推荐问题定时任务 */
    SUGGEST_QUESTION_JOB("推荐问题定时任务"),

    /** 问题聚类定时任务 */
    QUESTION_ClUSTER_JOB("问题聚类定时任务");

    /** 定时任务名称 */
    private String jobName ;

    JobTypeEnum(String jobName){
        this.jobName = jobName ;
    }


    public String getJobName() {
        return jobName;
    }
}
