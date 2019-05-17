package com.cloud.aiassistant.stagging.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体对象
 * Created by ChengYun on 2018/11/25 Version 1.0
 */
@Data
public class User implements Serializable{

    private static final long serialVersionUID = 8438189850626564518L;

    /** 用户的唯一标识：数据库主键 */
    private int id;

    /** 用户名 */
    private String name ;

    /** 年龄 */
    private int age ;

    /** 地址 */
    private String address ;

    public User() {    }

    public User(String userName, int userAge) {
        this.name=userName;
        this.age=userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
