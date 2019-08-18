package com.cloud.experience.jdkknowledge.hashmap;

import java.util.Map;

/** Jdk8 HashMap 分解详解 */
public class HashMapAnalysis<K,V> {

    /** Node实现了Map.Entry<K,V>用于数据存储键值对,如果K的hashCode相同,存储在Node实例中链表结构next当中 */
    transient Node<K,V>[] table;

    /** 当前Map的键值对的数量 */
    transient int size;

    /** Map需要扩容的负载因子，默认0.75 */
    final float loadFactor = 0.75f ;

    /** 下一个元素大小,如果达到扩容数字(容量*加载因子),需要resize(双倍扩容) */
    int threshold;

    /** HashMap中存储对象Node对象结构 */
    private class Node<K, V> implements Map.Entry<K,V>{
        /** 当前Key的hashCode值 */
        final int hash;
        /** 当前Entry中Key对象 */
        final K key;
        /** 当前Entry中Value对象 */
        V value;
        /** 当前Entry中Key的hashCode一样Entry的下一个Entry(链表存储) */
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;  this.key = key;  this.value = value;  this.next = next;
        }

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }
}
