package com.cloud.practice.springboot.demo.pojo;

/**
 * Created by ChengYun on 2017/6/13 Vesion 1.0
 */
public class OrderDemo {

    private String orderNumber ;

    private String orderName;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "OrderDemo{" +
                "orderNumber='" + orderNumber + '\'' +
                ", orderName='" + orderName + '\'' +
                '}';
    }
}
