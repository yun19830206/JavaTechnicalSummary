package com.cloud.experience.jdk7gc.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 监控Web应用的各项资源占用情况<br/>
 *  cpu状态，总内存状态，堆内存状态，非堆内存状态，线程数，GC次数
 * @author ChengYun
 * @date 2017/9/10 Version 1
 */
@Component
@Slf4j
public class WebResourceMonitor {

    @Scheduled(cron="0/60 * *  * * ? ")   //每60秒执行一次,用于收集统计应用服务器信息
    /**  CRON表达式    含义
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
    public void run(){
        log.debug("\n");
//        log.debug("=============进入测试：内存情况(在用)============");
//        log.debug("堆总：" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
//        log.debug("堆闲：" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
//        log.debug("堆用："
//                + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "M");
//
//        log.debug("=============进入测试：线程占用情况============");
//        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//        while(threadGroup.getParent() != null){
//            threadGroup = threadGroup.getParent();
//        }
//        int totalThread = threadGroup.activeCount();
//        log.debug("总线程数："+totalThread);
//
//        log.debug("=============Java虚拟机============");
//        log.debug("Java虚拟机内存总量：" + (Runtime.getRuntime().totalMemory()
//                + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()) / 1024 / 1024 + "M");
//        log.debug("Java虚拟机空闲内存：" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
//
//        log.debug("=============分配的堆内存当前使用量============");
//        MemoryUsage heapUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
//        log.debug("可用于内存管理的最大内存量：" + heapUsage.getMax() / 1024 / 1024 + "M");
//        log.debug("初始内存大小：" + heapUsage.getInit() / 1024 / 1024 + "M");
//        log.debug("当前已使用的内存量：" + heapUsage.getUsed() / 1024 / 1024 + "M");
//        log.debug("Java虚拟机能使用的内存量：" + heapUsage.getCommitted() / 1024 / 1024 + "M");
//
//        log.debug("=============分配的非堆内存当前使用量============");
//        MemoryUsage noHeapUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
//        log.debug("可用于内存管理的最大内存量：" + noHeapUsage.getMax() / 1024 / 1024 + "M");
//        log.debug("初始内存大小：" + noHeapUsage.getInit() / 1024 / 1024 + "M");
//        log.debug("当前已使用的内存量：" + noHeapUsage.getUsed() / 1024 / 1024 + "M");
//        log.debug("Java虚拟机能使用的内存量：" + noHeapUsage.getCommitted() / 1024 / 1024 + "M");
//
//        log.debug("=============Class Loader信息收集============");
//        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
//        log.debug("Java虚拟机已加载类的总数：" + classLoadingMXBean.getLoadedClassCount());
//        log.debug("Java虚拟机从启动开始已加载的类总数：" + classLoadingMXBean.getTotalLoadedClassCount());
//        log.debug("Java虚拟机从启动开始已卸载的类总数：" + classLoadingMXBean.getUnloadedClassCount());
//
//        log.debug("=============线程监控信息(在用)============");
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        log.debug("线程数：" + threadMXBean.getThreadCount());
//        log.debug("线程峰值：" + threadMXBean.getPeakThreadCount());
//        log.debug("守护线程数：" + threadMXBean.getDaemonThreadCount());

        log.debug("=============垃圾回收期信息============");
        List<GarbageCollectorMXBean> garbageCollectorMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeanList){
            String name = garbageCollectorMXBean.getName();
            log.debug("垃圾回收器的名称：" + name);
            log.debug("垃圾回收器已回收的总次数：" + garbageCollectorMXBean.getCollectionCount());
            log.debug("垃圾回收器已回收的总时间：" + garbageCollectorMXBean.getCollectionTime()/1000+"秒");
        }
        log.debug("\n");
    }


}
