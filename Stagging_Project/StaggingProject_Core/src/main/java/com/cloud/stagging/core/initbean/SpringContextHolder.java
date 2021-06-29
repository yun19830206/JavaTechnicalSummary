package com.cloud.stagging.core.initbean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * @author ChengYun
 * @date 2020/4/15  Vesion 1.0
 */
public class SpringContextHolder implements ApplicationContextAware {
    /** Spring ApplicationContext */
    private static ApplicationContext applicationContext ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获得Spring ApplicationContext
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return SpringContextHolder.applicationContext;
    }

    /**
     * 根据类型来获得Spring管理的Bean
     * @param clazz 需要获得Bean类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T getBean(Class<T> clazz){
        return SpringContextHolder.applicationContext.getBean(clazz);
    }

}
