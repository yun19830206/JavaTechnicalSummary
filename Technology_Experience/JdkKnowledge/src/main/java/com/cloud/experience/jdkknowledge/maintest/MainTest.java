package com.cloud.experience.jdkknowledge.maintest;

import com.cloud.experience.jdkknowledge.pojo.HashCodePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author ChengYun
 * @date 2019/4/25  Vesion 1.0
 */
public class MainTest {


    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        mainTest.testHashMapStore();
        mainTest.testJson2String();
        String string = new String("2323");
    }

    private void testJson2String() {

    }


    private void testHashMapStore() {
        HashMap<HashCodePojo,String> hashMap = new HashMap<>();
        hashMap.put(new HashCodePojo("123"),"123");
        hashMap.put(new HashCodePojo("12345"),"12345");
        hashMap.put(new HashCodePojo("1"),"1");
        hashMap.put(new HashCodePojo("12345678901"),"12345678901");
        hashMap.put(new HashCodePojo("123456789012345678901"),"123456789012345678901");
        hashMap.put(new HashCodePojo("12345"),"121");
        hashMap.put(new HashCodePojo("12345"),"1221");
        hashMap.put(new HashCodePojo("12345"),"12sdfsdf");
        System.out.println(hashMap);

        Set<HashCodePojo> set = new TreeSet<>();
        set.add(new HashCodePojo("123"));

        ArrayList<HashCodePojo> arrayList = new ArrayList<>();
        arrayList.contains(new HashCodePojo("123"));
    }
}
