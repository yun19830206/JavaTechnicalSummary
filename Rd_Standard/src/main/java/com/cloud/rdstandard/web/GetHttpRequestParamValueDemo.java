package com.cloud.rdstandard.web;

import com.cloud.rdstandard.pojo.ApiResponse;
import com.cloud.rdstandard.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 本Controller的作用：SpringMVC请求参数获取的几种方法。
 * @author ChengYun
 * @date 2018/11/25 Version 1
 */
@RestController
@Slf4j
@RequestMapping("/demo/web/getrequestvalue")
public class GetHttpRequestParamValueDemo {

    private static String logSintring = "请求参数中User对象为：" ;

    /**
     * 首先介绍几种常见的Request请求方式，以PostMan发起请求为例：参照《https://blog.csdn.net/xiao__jia__jia/article/details/79357274》
     *   get方法：最为简单，既将参数添加到请求URL当中，如：http://localhost:8080/demo/web/getrequestvalue/addUserByBeanUseRequestBody?age=10&name=yun
     *   post(Content-Type: application/x-www-form-urlencoded)：浏览器的原生 form 表单，如果不设置 enctype 属性，那么最终就会以 application/x-www-form-urlencoded 方式提交数据。请求类似于下面这样(略掉无关的请求头)：
     *       POST http://www.example.com HTTP/1.1
     *       Content-Type: application/x-www-form-urlencoded;charset=utf-8
     *       title=test&sub%5B%5D=1&sub%5B%5D=2&sub%5B%5D=3
     *   post(Content-Type: multipart/form-data)：使用表单上传文件时，必须让 form 的 enctyped 等于这个值。用于上传文件与KeyValue共同存在的请求。
     *   post(Content-Type: application/json)：现在越来越多的人把它作为请求头，用来告诉服务端消息主体是序列化后的 JSON 字符串。使用最为广泛
     */


    /**
     * 最原始的方法，在业务代码开发中，不推荐使用，无法到达高效开发的目的:</br>
     * 通过HttpServletRequest接收：
     *  get方式可以获得参数的值
     *  post方式(Content-Type: application/x-www-form-urlencoded)可以获得参数的值
     *  post方式(Content-Type: multipart/form-data)可以获得参数的值
     *  post方式(Content-Type: application/json)无法获得参数的值
     */
    @RequestMapping("/addUserByServletRequest")
    public ApiResponse addUserByServletRequest(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        log.debug(logSintring+user);
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

    /**
     * 将请求Key作为接口入参方式，也是较为传统的方法，无法到达高效开发的目的:</br>
     *  get方式可以获得参数的值
     *  post方式(Content-Type: application/x-www-form-urlencoded)可以获得参数的值
     *  post方式(Content-Type: multipart/form-data)可以获得参数的值
     *  post方式(Content-Type: application/json)无法获得参数的值，报错："Optional int parameter '**' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type."
     */
    @RequestMapping("/addUserByKeyByKey")
    public ApiResponse addUserByKeyByKey(String name, int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        log.debug(logSintring+user);
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

    /**
     * 通过POJOBean作为RestApi接口的入参(SpringMVC做映射)，此种方式在实际业务开发中较为实用:</br>
     *  get方式可以获得参数的值
     *  post方式(Content-Type: application/x-www-form-urlencoded)可以获得参数的值
     *  post方式(Content-Type: multipart/form-data)可以获得参数的值
     *  post方式(Content-Type: application/json)无法获得参数的值。这种方式必须使用@RequestBody注解在入参当中。
     */
    @RequestMapping("/addUserByBean")
    public ApiResponse addUserByBean(User user){
        log.debug(logSintring+user);
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

    /**
     * 通过POJOBean前面加@RequestBody注解作为RestApi接口的入参(SpringMVC做映射)，此种方式在实际业务开发中较为实用，配合研发规范，可以达到较高效开发效率:</br>
     *  get方式不支持，报错：Required request body is missing:
     *  post方式(Content-Type: application/x-www-form-urlencoded)不可以，报错：Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
     *  post方式(Content-Type: multipart/form-data)不可以，报错：Content type 'multipart/form-data;boundary=----WebKitFormBoundaryQQDXKavdsLyPAphR;charset=UTF-8' not supported
     *  post方式(Content-Type: application/json)可以获得参数的值
     */
    @RequestMapping("/addUserByBeanUseRequestBody")
    public ApiResponse addUserByBeanUseRequestBody(@RequestBody User user){
        log.debug(logSintring+user.toString());
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

    /**
     * 通过@PathVariable注解获得请求URL中的参数值，此种方式在实际业务开发中较为少见，此种情况符合Restful规范，但是使用不是很方便，尤其是在做接口自动化测试的时候，数据加密也比较困难，建议较少使用:</br>
     *  get方式支持，需要请求URL路径中有这些信息
     *  post方式：不管是哪种方式都不支持。 如果post请求的URL路径符合PathVariable也是规则，也是可以获得参数的
     */
    @RequestMapping("/addUserByPathValue/{useName}/{userAge}")
    public ApiResponse addUserByPathValue(@PathVariable String useName, @PathVariable("userAge") int age){
        User user = new User();
        user.setAge(age);
        user.setName(useName);
        log.debug(logSintring+user);
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

    /**
     * 通过@RequestParam注解获得请参数值，此种方式在实际业务开发中使用较多，此种情况符合Restful规范，但是使用不是很方便，尤其是在做接口自动化测试的时候，数据加密也比较困难，建议较少使用:</br>
     *  get方式支持，需要请求URL路径中有这些信息
     *  post方式(Content-Type: application/x-www-form-urlencoded)可以。 但是 required = false, defaultValue = "99" 不起作用，即age必须要有此请求参数
     *  post方式(Content-Type: multipart/form-data) 可以。 但是 required = false, defaultValue = "99" 不起作用，即age必须要有此请求参数
     *  post方式(Content-Type: application/json) 不可以。
     */
    @RequestMapping("/addUserByRequestParam")
    public ApiResponse addUserByRequestParam(@RequestParam("name") String userName,
                                             @RequestParam(value = "age", required = false, defaultValue = "99") int userAge){
        User user = new User(userName,userAge);
        log.debug(logSintring+user);
        return new ApiResponse.ApiResponseBuilder().data(user).build();
    }

}
