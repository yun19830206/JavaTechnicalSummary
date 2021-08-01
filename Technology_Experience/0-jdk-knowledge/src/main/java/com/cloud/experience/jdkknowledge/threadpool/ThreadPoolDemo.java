package com.cloud.experience.jdkknowledge.threadpool;

import com.cloud.experience.jdkknowledge.AjaxResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本类介绍线程池相关知识点
 * @author ChengYun
 * @date 2021/7/26  Vesion 1.0
 */
@RestController
public class ThreadPoolDemo {


    /**
     * ############此处总体介绍线程时通用相关知识点#################
       线程池不允许使用Executors去创建(ExecutorService taskExe = Executors.newFixedThreadPool(10))，
       而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
       说明：Executors返回的线程池对象的弊端如下：
         1）FixedThreadPool和SingleThreadPool:
            允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
         2）CachedThreadPool:
            允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。
       通过ThreadPoolExecutor的方式创建的线程池的核心7个参数含义如下：
       ExecutorService threadPool=new ThreadPoolExecutor(
         2,       #1、corePoolSize线程池的核心线程数
         5,       #2、maximumPoolSize能容纳的最大线程数
         1L,TimeUnit.SECONDS,     #3、keepAliveTime空闲线程存活时间;  4、unit 存活的时间单位
         new LinkedBlockingQueue<>(3),      #5、workQueue 存放提交但未执行任务的队列
         Executors.defaultThreadFactory(),  #6、threadFactory 创建线程的工厂类
         new ThreadPoolExecutor.AbortPolicy());   #7、handler 等待队列满后的拒绝策略
     *
     */




    /**
     * TODO 这里详细介绍ScheduledExecutorService的各个知识点
     */
    @RequestMapping("/threadpool/startScheduledExecutorService")
    public AjaxResponse<?> startScheduledExecutorService(){

        Lock lock = new ReentrantLock();
        lock.lock();
        //critical section
        lock.unlock();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("延时、定时执行线程池ScheduledExecutorService子线程");
                        t.setDaemon(true);  //设置线程为“守护线程”
                        return t;
                    }
                });



        return AjaxResponse.success("场景介绍延时、定时执行线程池ScheduledExecutorService 开启成功。");
    }

}
