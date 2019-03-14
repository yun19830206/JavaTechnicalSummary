package com.cloud.rdstandard.stagging.pojo;

import lombok.Data;

/**
 * 用户实体对象
 * Created by ChengYun on 2018/11/25 Version 1.0
 */
@Data
public class User {

    /** 用户的唯一标识：数据库主键 */
    private int id;

    /** 用户名 */
    private String name ;

    /** 年龄 */
    private int age ;

    public User() {    }

    public User(String userName, int userAge) {
        this.name=userName;
        this.age=userAge;
    }
}
