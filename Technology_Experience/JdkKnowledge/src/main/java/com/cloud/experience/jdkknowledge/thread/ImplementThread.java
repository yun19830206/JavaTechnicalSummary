package com.cloud.experience.jdkknowledge.thread;

/**
 * 通过实现接口Runnable接口
 * @author ChengYun
 * @date 2019/5/4  Vesion 1.0
 */
public class ImplementThread implements Runnable{
    @Override
    public void run() {
        System.out.println(ImplementThread.class+"开始执行:"+System.currentTimeMillis());
    }

    public static void main(String[] args) {
        ImplementThread extendThread = new ImplementThread();
        Thread runabbleThread = new Thread(extendThread);
        System.out.println("准备执行:"+System.currentTimeMillis());
        runabbleThread.start();
        System.out.println("准备结束:"+System.currentTimeMillis());
    }
}
