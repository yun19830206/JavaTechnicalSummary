package com.cloud.experience.jdk7gc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Spring RestTemplate工具类，简单封装
 * Created by ChengYun on 2017/9/3 Version 1.0
 */
@Slf4j
public class CSRestTemplateUtils {
    private CSRestTemplateUtils(){}

    /**
     * RestTemplate(应用唯一)请求requestUrl地址，request是请求Post体，responseType返回值类型，
     * @param restTemplate Spring管理的实例化好的RestTemplate Bean
     * @param requestUrl http请求url
     * @param request   http post 请求的Object
     * @param responseType  http post 请求返回结果类型
     * @param <T> http post 请求返回结果类型
     * @return T http post 请求返回结果
     */
    public static <T> T post(RestTemplate restTemplate, String requestUrl, Object request, Class<T> responseType) {
        log.debug("CSRestTemplateUtils Post Start for url：{}, request object is:{}",requestUrl,request);
        T t = restTemplate.postForObject(requestUrl,request,responseType) ;
        log.debug("CSRestTemplateUtils Post End for url：{}, response object is:{}",requestUrl,t);
        return t ;
    }

    /**
     * RestTemplate(应用唯一)请求requestUrl地址，request是请求Post体，responseType返回值类型，
     * @param restTemplate Spring管理的实例化好的RestTemplate Bean
     * @param requestUrl http请求url
     * @param request   http post 请求的Object
     * @param responseType  http post 请求返回结果类型
     * @param <T> http post 请求返回结果类型
     * @return T http post 请求返回结果
     */
    public static <T> T get(RestTemplate restTemplate, String requestUrl, Object request, Class<T> responseType) {
        log.debug("CSRestTemplateUtils Get Start for url：{}, request object is:{}",requestUrl,request);

        //请求头信息都放在此，包括数据类型， cookie等
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<T> httpEntity = new HttpEntity<>(httpHeaders);

        //http get方式请求，请求参数只支持Map<String,简单数据类型>
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl);
        if(null != request && request instanceof Map){
            Map<String,?> paramMap = (Map<String,?>)request;
            if(paramMap.size()>0){
                for(Map.Entry<String,?> param : paramMap.entrySet()){
                    urlBuilder.queryParam(param.getKey(),param.getValue());
                }
            }
        }

        final URI getUrl = urlBuilder.build().encode().toUri();
        T t = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, responseType).getBody();

        log.debug("CSRestTemplateUtils Get End for url：{}, response object is:{}",requestUrl,t);
        return t ;
    }
}
