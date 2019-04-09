# Spring  Boot设置SessionKey的方式：
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








# 另外一个知识点：
  1. 内容记录，受限域的研发规范
  2. 内容记录，受限域的研发规范
  3. 内容记录，受限域的研发规范