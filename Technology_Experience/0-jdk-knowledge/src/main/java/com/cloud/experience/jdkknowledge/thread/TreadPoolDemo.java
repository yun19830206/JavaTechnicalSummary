package com.cloud.experience.jdkknowledge.thread;

/**
 * 本类详细讲解线程池相关知识点<br/>。
 * 阿里巴巴研发规范明确表示：
     线程池不允许使用Executors去创建，而是通过TreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
     说明：Executors各个方法的弊端
          1)newFixedThreadPool和newSingleThreadExecutor:主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM
           2)newCachedThreadPool和newScheduledThreadPool:主要问题是线程数最大数是Integer.MAX_VALUE,可能会创建数量非常多的线程，甚至OOM
 * @author ChengYun
 * @date 2020/2/9  Vesion 1.0
 */
public class TreadPoolDemo {
}
