## 项目功能说明
    本项目重在模拟Java中常见的java.lang.OutOfMemoryError错误,主要有三种:
      java.lang.OutOfMemoryError： unable to create new native thread
      java.lang.OutOfMemoryError: Java heap space
      java.lang.OutOfMemoryError: PermGen space PermGen space
  
###  接口说明
    http://127.0.0.1:9000/normalRequest
        --一个正常的接口调用
    http://127.0.0.1:9000/createOneCanotGCBigObject
        --创建一个不能被GC回收的对象，这种操作方式，特别需要注意，在代码中需要避免
    http://127.0.0.1:9000/createOneCanBeGCBigObject
        --创建一个能被GC回收的对象，这种对象的存货周期只存在方法体当中
    http://127.0.0.1:9000/createOneCanotGC10MObject
        --创建一个不能被GC回收的小对象(10M)
    http://127.0.0.1:9000/stackOver
        --一个方法调用级别的会无限循环的接口调用

###  项目验证参数信息: Mmx=1.1G,大接口创建对象700M    
###  验证能被GC回收接口--调用多次，没事
    http://127.0.0.1:9000/createOneCanBeGCBigObject
        -->无论调用多少次之后，服务都是正常运行，可以在Java VisualVM当中强制回收掉。

###  验证不能被GC回收接口--出现java.lang.OutOfMemoryError: Java heap space
    http://127.0.0.1:9000/createOneCanotGCBigObject
        -->第一次调用可以，服务没有异常，没办法在Java VisualVM当中强制回收掉
    http://127.0.0.1:9000/normalRequest
        -->调用正常接口，可以正常返回。(此时JVM还剩300M左右的空闲量)
    http://127.0.0.1:9000/createOneCanotGCBigObject
        -->第二次调用无法回收创建大对象的接口，后台直接报.OutOfMemoryError: Java heap space
    http://127.0.0.1:9000/normalRequest
        -->虽然已经报了OOM,但是由于JDK8的保护机制，其他正常接口，可以正常返回的,如果是低版本JDK应用就不能使用了。(此时JVM还剩300M左右的空闲量)
        
###  验证unable to create new native thread类型的问题
    http://127.0.0.1:9000/createOneCanotGCBigObject
        -->第一次调用可以，服务没有异常，没办法在Java VisualVM当中强制回收掉
    http://127.0.0.1:9000/normalRequest
        -->调用正常接口，可以正常返回。(此时JVM还剩300M左右的空闲量)
    http://127.0.0.1:9000/debugCreateMaxThread
        -->创建一万个无法结束的线程的接口，也就是查看本应用在现有空闲内存下，看还能创建多少线程(Root账号创建系统较多后Web应用进程执行被Kill,非Root账号运行，可以复现标题的错误)