## JDK1.5 并发工具包之--ExecutorService(线程池介绍)介绍
* 【Executor接口】：Executor提供了一系列简便的方法，来帮助我们创建ThreadPool。  .execute(Runnable);方法用于执行一个线程。
* 【ExecutorService接口】：ExecutorService(执行器服务接口)继承了Executor,提供了更多的线程池的操作,是对Executor的补充。ExecutorService接口表示一个异步执行机制，使我们能够在后台执行任务。因此一个ExecutorService很类似于一个线程池。实际上，存在于java.util.concurrent包里的ExecutorService实现就是一个线程池实现。ExecutorService的实现有ThreadPoolExecutor类与ScheduledThreadPoolExecutor类。
* 【ThreadPoolExecutor类】：ThreadPoolExecutor线程池执行者类是ExecutorService接口的一个实现，使用其内部池中的线程执行给定任务(Callable 或者 Runnable)，它可以控制这7个参数：corePoolSize,maximumPoolSize,keepAliveTime,keepAliveTimeUnit,workBlockingQuereQueue,threadFactory,rejectedExecutionHandler。 
* 【ScheduledExecutorService类】：ScheduledExecutorService定时执行者服务是ExecutorService接口的一个实现，它能够将任务延后执行，或者间隔固定时间多次执行。 核心方法如下：
```java
.schedule(Callable task, long delay, TimeUnit timeunit)   #任务Callable在给定的延迟之后执行。这个方法返回一个 ScheduledFuture，ScheduledFuture.get()可以获得他的执行结果，如果没有执行完成此方法等待到callable执行完成。
.schedule(Runnable task, long delay, TimeUnit timeunit)   #同Callable入参方法，区别在Runnable方法没有返回值，即任务执行结束之后ScheduledFuture.get()返回null
.scheduleAtFixedRate(Runnable, long initialDelay, long period, TimeUnit timeunit)     #任务将被定期执行。该任务将会在首个initialDelay之后得到执行，然后每个period 时间之后重复执行。如果任务执行抛出异常，该任务将不再执行。如果没有任何异常的话，这个任务将会持续循环执行到ScheduledExecutorService被关闭。如果一个任务执行占用了比计划的时间间隔更长的时候，下一次执行将在当前执行结束执行才开始。计划任务在同一时间不会有多个线程同时执行。
.scheduleWithFixedDelay(Runnable, long initialDelay, long period, TimeUnit timeunit)  #大部分同scheduleAtFixedRate(),唯有period被解释为前一个执行的结束和下一个执行的结束之间的间隔。因此这个延迟是执行结束之间的间隔，而不是执行开始之间的间隔。
```
* 【Executors类】：Executors是一个工具类，采用工程模式快速快速四种线程池：(阿里巴巴规范不建议使用此方法创建线程池，而通过ThreadPoolExecutor构造参数方式显式创建) 
```java
Executors.newSingleThreadExecutor();  #创建一个单线程的线程池，它只会用唯一的工作线程来执行任务，也就是相当于单线程串行执行多有任务，如果这个唯一的线程以为异常结束，那么会有一个新的线程来代替它。
Executors.newFixedThreadPool(2);      #创建一个定长的线程池，可控制线程最大并发数，超出的线程会在队列中等待。使用这个线程池的时候，必须根据实际情况估算出线程的数量。 
Executors.newCachedThreadPool();      #创建一个可缓存线程池，如果线程池的长度超过处理需要，那么可灵活回收空闲线程，如果不可回收，那么新建线程。此线程池不会对线程池的大小限制，线程池的大小完全依赖与操作系统或者说JVM能够创建的最大线程大小，使用这种方式需要在代码运行的过程中通过控制并发任务的数量来控制线程的数量。 
Executors.newScheduledThreadPool(2);  #创建一个定长线程池，此线程池支持定时以及周期性执行任务的需求。 
```
