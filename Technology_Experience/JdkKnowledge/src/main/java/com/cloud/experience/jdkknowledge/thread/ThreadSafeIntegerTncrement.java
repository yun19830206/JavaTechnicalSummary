package com.cloud.experience.jdkknowledge.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全自增Integer方法验证
 * @author ChengYun
 * @date 2019/10/25  Vesion 1.0
 */
public class ThreadSafeIntegerTncrement {

    public static int count = 0;
    public static Counter counter = new Counter();
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    volatile public static int countVolatile = 0 ;


    public static void main(String[] args) throws InterruptedException {
        // 保证所有线程执行完毕
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0; i<10; i++){
            new Thread(){
                @Override
                public void run(){
                    for (int j = 0; j < 1000; j++) {
                        count++;
                        counter.increment();
                        atomicInteger.getAndIncrement();
                        countVolatile++;
                    }
                    countDownLatch.countDown();
                }
            }.start();
        }
        countDownLatch.await();
        System.out.println("static count: " + count);
        System.out.println("Counter: " + counter.getValue());
        System.out.println("AtomicInteger: " + atomicInteger.intValue());
        System.out.println("countVolatile: " + countVolatile);

    }

    public static class Counter {
        private int value;

        public synchronized int getValue() {
            return value;
        }

        public synchronized int increment() {
            return value++;
        }

        public synchronized int decrement() {
            return --value;
        }
    }
}
