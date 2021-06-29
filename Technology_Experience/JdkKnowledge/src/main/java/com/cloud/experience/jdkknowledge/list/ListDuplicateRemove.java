package com.cloud.experience.jdkknowledge.list;

/**
 * List去重的5种方式
 * @author ChengYun
 * @date 2020/12/14  Vesion 1.0
 */
public class ListDuplicateRemove {

    1.使用LinkedHashSet删除arraylist中的重复数据
    2.使用java8新特性stream进行List去重
    3.利用HashSet不能添加重复数据的特性 由于HashSet不能保证添加顺序，所以只能作为判断条件保证顺序：
            4.利用List的contains方法循环遍历,重新排序,只添加一次数据,避免重复：
            5.双重for循环去重
}
