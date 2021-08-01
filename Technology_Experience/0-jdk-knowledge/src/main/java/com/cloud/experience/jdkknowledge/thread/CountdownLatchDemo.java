package com.cloud.experience.jdkknowledge.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * CountdownLatch核心两个作用：1)多个线程等待同一个信号量一起工作。 2)多个线程全部做完了，才开始做主线程工作。
 *  CountDownLatch.countDown():计数器减一
 *  CountDownLatch.await():计数器到0时本代码行后面代码开始执行,否则本线程一直处于等待状态。
 * 案例场景：百米赛跑，4名运动员选手到达场地等待裁判口令，裁判一声口令，选手听到后同时起跑，当所有选手到达终点，裁判进行汇总排名
 * @author ChengYun
 * @date 2020/2/9  Vesion 1.0
 */
public class CountdownLatchDemo {
    public static void main(String[] args) {

        /** 线程池使用规范见：本子包中的TreadPoolDemo类 */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(" %d号运动员 ").build();
        ExecutorService threadPool =
                new ThreadPoolExecutor(4,8,0L,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1024),namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());

        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdPlayers = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = () -> {
                try {
                    System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                    cdOrder.await();
                    System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                    cdPlayers.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("线程出现了异常"+e.getMessage());
                }
            };
            threadPool.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判"+Thread.currentThread().getName()+"即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判"+Thread.currentThread().getName()+"已发送口令，正在等待所有选手到达终点");
            cdPlayers.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判"+Thread.currentThread().getName()+"汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("线程出现了异常"+e.getMessage());
        }
        threadPool.shutdown();
    }
}
