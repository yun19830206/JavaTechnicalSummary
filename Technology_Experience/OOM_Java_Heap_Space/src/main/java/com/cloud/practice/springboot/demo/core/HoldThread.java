package com.cloud.practice.springboot.demo.core;

import java.util.concurrent.CountDownLatch;

/**
 * 创建一个永远结束不了的线程，采用阻塞的方式
 * Created by ChengYun on 2017/10/24 Version 1.0
 */
public class HoldThread extends Thread{
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public HoldThread(){
        this.setDaemon(true);
    }

    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
        }
    }
}
