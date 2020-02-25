package com.cloud.experience.cachebreakdown.web;

import com.cloud.experience.cachebreakdown.cache.impl.RedisCacheImpl;
import com.cloud.experience.cachebreakdown.pojo.AjaxResponse;
import com.cloud.experience.cachebreakdown.pojo.BookCategrouyCount;
import com.cloud.experience.cachebreakdown.service.BookCategrouyCountServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.AbstractJackson2Decoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 为了复现缓存击穿问题的Controller
 * @author ChengYun
 * @date 2019/12/1  Vesion 1.0
 */
@RestController
@RequestMapping("/cachebreakdown")
public class CacheBreakDownController {

    public static final String CACHE_KEY_BOOK_CATEGROUY_COUNT = "cache:breakdown:categrouycount";

    @Autowired
    private BookCategrouyCountServer bookCategrouyCountServer ;

    @Autowired
    private RedisCacheImpl redisCache ;

    /** 不适用缓存，每次通过DB查询获得数据 */
    @RequestMapping("/getBookCategrouyCount/v1/directDB")
    public AjaxResponse getBookCategrouyCountDirectDB(){

        /** 不使用缓存获取数据方式 */
        List<BookCategrouyCount> categrouyCount = bookCategrouyCountServer.getCategrouyCount();
        return AjaxResponse.success(categrouyCount);

    }

    /** 仅仅使用缓存方式获得数据 */
    @RequestMapping("/getBookCategrouyCount/v2/useCache")
    public AjaxResponse getBookCategrouyCountUseCache(){

        /** 适用缓存获取数据方式 */
        List<BookCategrouyCount> categrouyCount = null ;
        categrouyCount = redisCache.getValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,List.class);
        if(null == categrouyCount){
            //这里假设了bookCategrouyCountServer.getCategrouyCount();会直接一个慢SQL,导致数据库CPU飙到50%
            categrouyCount = bookCategrouyCountServer.getCategrouyCount();
            redisCache.setValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,categrouyCount,5*60);
        }
        return AjaxResponse.success(categrouyCount);
    }

    /** 使用缓存方式获得数据 增加同步代码块获得数据库数据 */
    @RequestMapping("/getBookCategrouyCount/v3/useCacheSync")
    public AjaxResponse getBookCategrouyCountUseCacheSync(){
        /** 适用缓存获取数据方式 */
        List<BookCategrouyCount> categrouyCount = null ;
        categrouyCount = redisCache.getValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,List.class);
        if(null == categrouyCount){
            //规避缓存获得不到数据，一下子全部怼到数据库压力太大，增加同步控制
            synchronized (this){
                categrouyCount = bookCategrouyCountServer.getCategrouyCount();
                redisCache.setValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,categrouyCount,5*60);
            }
        }
        return AjaxResponse.success(categrouyCount);
    }

    /** 使用缓存方式获得数据 增加同步代码块获得数据库数据，规避同步并发问题 */
    @RequestMapping("/getBookCategrouyCount/v4/useCacheSyncDetail")
    public AjaxResponse getBookCategrouyCountUseCacheSyncDetail(){
        /** 适用缓存获取数据方式 */
        List<BookCategrouyCount> categrouyCount = null ;
        categrouyCount = redisCache.getValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,List.class);
        if(null == categrouyCount){
            //规避缓存获得不到数据，一下子全部怼到数据库压力太大，增加同步控制
            synchronized (this){
                //同步代码中再次获取缓存数据，可以使用同步时候第一个线程放入缓存的结果，规避大量线程怼到数据库层面
                categrouyCount = redisCache.getValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,List.class);
                if(null != categrouyCount){
                    return AjaxResponse.success(categrouyCount);
                }
                categrouyCount = bookCategrouyCountServer.getCategrouyCount();
                redisCache.setValue(CACHE_KEY_BOOK_CATEGROUY_COUNT,categrouyCount,5*60);
            }
        }
        return AjaxResponse.success(categrouyCount);
    }

    /**
     * //TODO 1:从缓存获得结果有入侵业务代码了 不好。  2：对于特别热的Key在单Redis实例中解决办法(1:备份热Key,2:多级缓存本地二级缓存)
     */
}
