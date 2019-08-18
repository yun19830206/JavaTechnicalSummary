package com.cloud.experience.jdkknowledge.thread;

/**
 * 通过继承Tread方法新建一个实现类
 * @author ChengYun
 * @date 2019/5/4  Vesion 1.0
 */
public class ExtendThread extends Thread{

    public ExtendThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(ExtendThread.class+"开始执行:"+System.currentTimeMillis());
    }

    public static void main(String[] args) {
        ExtendThread extendThread = new ExtendThread("通过继承Tread方法新建一个实现类");
        System.out.println("准备执行:"+System.currentTimeMillis());
        extendThread.start();
        System.out.println("准备结束:"+System.currentTimeMillis());
    }
}
