# 本工程的作用：
> 本工程用于展示SpringBoot体系中常见知识点(复现、示例代码、解释)。

# 日志相关配置
> 见application.properties中《日志相关配置》章节





# SpringBoot的actuator监控(需要根据极客时间视屏中内容进行修改)
> * 引入Java包如下：
> * `<dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>`
> * 配置文件中增加无需登入鉴权：management.security.enabled=false
> * 可以使用get请求地址查看默认监控信息(health)：http://localhost:8080/actuator
> * 配置文件中增加更多监控信息配置：management.endpoints.web.exposure.include=*
> * 全量监控信息地址如下：http://localhost:8080/actuator/mappings(更多在actuator地址的结果中有href)
安全建议
针对 Spring Boot Actuator 提供的 endpoint，采取以下几种措施，可以尽可能降低被安全攻击的风险

最小粒度暴露 endpoint。只开启并暴露真正用到的 endpoint，而不是配置：management.endpoints.web.exposure.include=*。
为 endpoint 配置独立的访问端口，从而和 web 服务的端口分离开，避免暴露 web 服务时，误将 actuator 的 endpoint 也暴露出去。例：management.port=8099。
引入 spring-boot-starter-security 依赖，为 actuator 的 endpoint 配置访问控制。
慎重评估是否需要引入 spring-boot-stater-actuator。以我个人的经验，我至今还没有遇到什么需求是一定需要引入spring-boot-stater-actuator 才能解决，如果你并不了解上文所述的安全风险，我建议你先去除掉该依赖。
今天，你使用 Spring Boot Actuator 了吗？





# Spring体系各常见知识点明细
* SpringMVC请求参数获取的几种方法。：
    * 代码与说明地址：com.cloud.experience.spring.httprequest.GetHttpRequestParamValueDemo
    
# Spring Boot设置SessionKey的方式：
> * 整体参考网址:http://blog.csdn.net/yinyuehepijiu/article/details/50409570
> * Spring Boot中的添加方式--配置文件增加:
    server.session.cookie.name=robotchatsession 
    参考网址:https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/htmlsingle/#boot-features-customizing-embedded-containers
> * 普通Web工程添加方式--/MET-INF/context.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <Context sessionCookieName='robotchatsession'/>
    参考网页:http://tomcat.apache.org/tomcat-6.0-doc/config/context.html
> * WebShpere中的对SessionKey的设置:
    应用程序->企业应用程序-> [Application Server] ->会话管理->1.覆盖会话管理(需打钩).会话管理->2.启用 cookie(需打钩)->修改'Cookie路径' 
    参考网址:http://www.cnblogs.com/wenlong/p/3684233.html



