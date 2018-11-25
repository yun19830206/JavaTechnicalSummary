package com.cloud.experience.jdk7gc.service;

import com.cloud.experience.jdk7gc.utils.CSStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此Service模拟一个业务对象接口，只有一个接口，调用一次会往一个全集容器中增加一个10个1M的对象，
 * 这个容器达到1000个是会丢弃GC根引用(也就是可以被GC回收)
 * @author ChengYun
 * @date 2018/10/27 Version 1
 */
@Service
@Slf4j
public class BusinessService {


    /** 模拟不会被YouneGC回收的对象 */
    private ConcurrentHashMap<String,Object> canotBeRecoveryByYoungGC = new ConcurrentHashMap<>(8005);

    private AtomicLong mapKey = new AtomicLong();

    /** 搞个锁，预防切换canotBeRecoveryByYoungGC别打断 */
    private Lock lock = new ReentrantLock();

    /** 模拟业务操作 */
    public void doBusiness(){

        // 模拟垃圾回收需要的魔法值
        int businessTime = 10;
        int usedTimeBusinessTime = 1000 ;
        int maxMapKeyCount = 4500 ;

        for(int i =0 ; i< businessTime; i++){
            String keyString = CSStringUtils.connectString(mapKey.incrementAndGet(),"个Key");
            canotBeRecoveryByYoungGC.put(keyString,new byte[1024*1024]);
        }
        for(int i =0 ; i< usedTimeBusinessTime; i++){
            CSStringUtils.connectString(i,"个Key");
        }
        //如果数量大于4500个了(也就是4.5G大小了)，切换canotBeRecoveryByYoungGC，这样老的canotBeRecoveryByYoungGC就会被FullGC回收
        if (canotBeRecoveryByYoungGC.size() >= maxMapKeyCount && lock.tryLock()) {
            try {
                canotBeRecoveryByYoungGC = new ConcurrentHashMap<>(8005);
                log.info("将要做一次FullGC");
            } catch (Exception e) {
                log.error("切换ConcurrentHashMap错误："+e.getMessage());
            } finally {
                lock.unlock();
            }

        }

    }
}
