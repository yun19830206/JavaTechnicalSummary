package com.cloud.aiassistant.core.initbean;

import com.cloud.aiassistant.core.config.WxMappingJackson2HttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 初始化Bean工作(尤其是那些不愿意通过XML配置方式启动的Bean)
 * Created by ChengYun on 2017/8/22 Version 1.0
 */
@Configuration
@Slf4j
public class InitBeanToSpringContext {

    /**
     * 初始化Spring提供的Http访问句ResTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(10000);

//        // 添加转换器
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//        messageConverters.add(new SourceHttpMessageConverter());

//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
//                SerializerFeature.QuoteFieldNames, SerializerFeature.DisableCircularReferenceDetect);
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastMediaTypes.add(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE + ";charset=ISO-8859-1"));
//        fastConverter.setSupportedMediaTypes(fastMediaTypes);
//        messageConverters.add(fastConverter);


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        log.info("Success Create Spring RestTemplate Bean");
        return restTemplate ;
    }
}
