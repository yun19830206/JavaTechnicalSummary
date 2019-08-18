package com.cloud.experience.jdkknowledge.pojo;

/**
 * 一个pojo，用给将hashCode固定住10个数字之内
 * @author ChengYun
 * @date 2019/4/25  Vesion 1.0
 */
public class HashCodePojo implements Comparable{

    private String information ;

    public HashCodePojo(String information){
        this.information = information ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashCodePojo that = (HashCodePojo) o;

        return information != null ? information.equals(that.information) : that.information == null;
    }

    @Override
    public int hashCode() {
//        int length = this.information.length();
//        if(length<10){
//            return length ;
//        }else{
//            return length%10 ;
//        }
        int hashCode = this.information.length()%10;
        return hashCode ;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
