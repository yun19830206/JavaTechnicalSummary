# 本类详细介绍多线程并发相关知识点
> 本类详细介绍多线程并发相关知识点


## 线程各关键字介绍
* 【Thread】：等等等
* 【Callable】：等等等
* 【Runnable】：等等等
* 【ThreadLocal】：等等等


## JDK1.5 并发工具包之--BlockingQueue介绍
* 【阻塞队列BlockingQueue接口】：BlockingQueue接口表示一个线程安放入和提取实例的队列
* 【数组阻塞队列ArrayBlockingQueue实现】：ArrayBlockingQueue是一个有界的阻塞队列，其内部实现是将对象放到一个数组里。一旦初始化，大小就无法修改。 内部以 FIFO(先进先出)的顺序对元素进行存储。队列中的头元素在所有元素之中是放入时间最久的那个，而尾元素则是最短的那个。
* 【延迟队列DelayQueue实现】：DelayQueue延迟队列持有实现了Delayed接口的元素，在每个元素getDelay()方法返回值之后释放该元素，如果返回的是 0 或者负值，延迟将被认为过期，该元素将会在 DelayQueue的下一次 take被调用的时候被释放掉。
* 【链阻塞队列LinkedBlockingQueue实现】：LinkedBlockingQueue 内部以一个链式结构(链接节点)对其元素进行存储。可设置链式结构上限。如果没有定义上限，将使用 Integer.MAX_VALUE 作为上限。内部以 FIFO(先进先出)的顺序对元素进行存储。
* 【具有优先级的阻塞队列PriorityBlockingQueue实现】：PriorityBlockingQueue是一个无界的并发队列。无法插入null 值。元素必须实现Comparable接口。
* 【同步队列SynchronousQueue实现】：SynchronousQueue是一个同时只能够容纳单个元素的队列。如果该队列已有一元素的话，试图向队列中插入一个新元素的线程将会阻塞。如果该队列为空，试图向队列中抽取一个元素的线程将会阻塞。
* 【阻塞双端队列BlockingDeque】：BlockingDeque 类是一个双端队列，在不能够插入元素时，它将阻塞住试图插入元素的线程；在不能够抽取元素时，它将阻塞住试图抽取的线程。双端队列是一个你可以从任意一端插入或者抽取元素的队列。
* 【链阻塞双端队列LinkedBlockingDeque】：以链式数据结构实现的双端阻塞队列
* 【阻塞队列BlockingQueue】的核心方法如下
```java
BlockingQueue.add(o);    #插入元素，如果试图的插入操作如法立即执行，抛一个异常
BlockingQueue.offer(o);  #插入元素，如果试图的插入操作如法立即执行，返回一个特定的值(常常是true/false)
BlockingQueue.put(o);    #插入元素，如果试图的插入操作如法立即执行，该方法调用将会发生阻塞，直到能够执行
BlockingQueue.offer(o,timeout,timeuint);    #插入元素，如果试图的插入操作如法立即执行，该方法调用将会发生阻塞，直到能够执行,但等待超时时，返回一个特定值以告知该操作是否成功(true/false)
BlockingQueue.remove(o); #移除元素，如果试图的移除操作如法立即执行，抛一个异常
BlockingQueue.poll(o);   #移除元素，如果试图的移除操作如法立即执行，返回一个特定的值(常常是true/false)
BlockingQueue.take(o);   #移除元素，如果试图的移除操作如法立即执行，该方法调用将会发生阻塞，直到能够执行
BlockingQueue.poll(o,timeout,timeuint);    #移除元素，如果试图的移除操作如法立即执行，该方法调用将会发生阻塞，直到能够执行,但等待超时时，返回一个特定值以告知该操作是否成功(true/false)
BlockingQueue.element(o);  #检查元素，如果试图的检查操作如法立即执行，抛一个异常
BlockingQueue.peek(o);     #检查元素，如果试图的检查操作如法立即执行，返回一个特定的值(常常是true/false)
```


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


## JDK1.5 并发工具包之--锁对象介绍
* 【Lock锁接口】：Lock是类似于synchronized的线程同步机制。但Lock比synchronized块更加灵活、精细。其实现类是ReentrantLock。一个 Lock 对象和一个 synchronized 代码块之间的主要不同点是。
```Json
1)synchronized代码块不能够保证进入访问等待的线程的先后顺序。
2)你不能够传递任何参数给一个synchronized代码块的入口。因此对于synchronized代码块的访问等待设置超时时间是不可能的事情。
3)synchronized块必须被完整地包含在单个方法里。而一个Lock对象可以把它的lock()和unlock()方法的调用放在不同的方法里。
```
* 【Lock锁的重要方法如下】：  
```Java
lock()     #将Lock实例锁定。如果该Lock实例已被锁定，调用lock()方法的线程将会阻塞，直到Lock实例解锁。
tryLock()  #方法试图立即锁定Lock实例。如果锁定成功，它将返回true，如果Lock实例已被锁定该方法返回false。这一方法永不阻塞。
tryLock(timeout,TimeUnit) #工作类似于tryLock()方法，除了它在放弃锁定Lock之前等待一个给定的超时时间之外。
unlock()   #改方法对Lock实例解锁。一个Lock实现将只允许锁定了该对象的线程来调用此方法。其他(没有锁定该Lock对象的线程)线程对unlock()方法的调用将会抛一个未检查异常(RuntimeException)
lockInterruptibly()  #方法将会被调用线程锁定，除非该线程被打断。
```
* 【ReadWriteLock读写锁接口】：ReadWriteLock读写锁是一种先进的线程锁机制。它能够允许多个线程在同一时间对某特定资源进行读取，但同一时间内只能有一个线程对其进行写入。读写锁的理念在于多个线程能够对一个共享资源进行读取，而不会导致并发问题。
* 【ReadWriteLock读锁说明】：如果没有任何写操作线程锁定ReadWriteLock，并且没有任何写操作线程要求一个写锁(但还没有获得该锁)就可以获得读锁。因此，可以有多个读操作线程对该锁进行锁定。
* 【ReadWriteLock写锁说明】：如果没有任何读操作或者写操作就可以获得写锁。因此，在写操作的时候，只能有一个线程对该锁进行锁定。
* 【ReadWriteLock方法说明】：ReentrantReadWriteLock是此接口的实现。.readLock().lock()与.readLock().lock();是对读锁的获得与释放方法；.writeLock().lock()与.writeLock().unlock();是对写锁的获得与释放方法。 


## JDK1.5 并发工具包之--常用(ConcurrentMap、AtomicBoolean、AtomicInteger..)对象介绍介绍
* 【ConcurrentHashMap】：ConcurrentHashMap能够提供比HashTable更好的并发性能。读操作ConcurrentHashMap并不会把整个Map 锁住，写操作时也不会锁住整个Map，它的内部只是把Map中正在被写入的部分进行锁定。另外一个不同点是，在被遍历的时候，即使是 ConcurrentHashMap被改动，它也不会抛ConcurrentModificationException。
* 【并发导航映射ConcurrentNavigableMap】：ConcurrentNavigableMap是一个支持并发访问的NavigableMap，它还能让它的子map具备并发访问的能力。所谓的"子map"指的是诸如headMap()、subMap()、tailMap()之类的方法返回的map。
* 【原子性布尔 AtomicBoolean】：补充内容
* 【原子性整型 AtomicInteger】：补充内容
* 【原子性长整型 AtomicLong】：
* 【原子性引用型 AtomicReference】：


## JDK1.5 并发工具包之--其他工作介绍
* 【闭锁 CountDownLatch】：CountDownLatch是一个并发构造，它以一个给定的数量初始化，countDown()每被调用一次，这一数量就减一，通过调用 await() 方法之一，线程可以阻塞等待这一数量到达零。
* 【栅栏 CyclicBarrier】：CyclicBarrier 类是一种同步机制，它是一个所有线程必须等待的一个栅栏，直到所有线程都到达这里，然后所有线程才可以继续做其他事情。通过new CyclicBarrier(2);初始化等待栅栏的线程数，通过调用CyclicBarrier.await() 方法，两个线程可以实现互相等待。
* 【交换机 Exchanger】：Exchanger类表示一种两个线程可以进行互相交换对象的会和点。在两个线程之间可以持有Exchanger对象。
* 【信号量 Semaphore】：Semaphore类是一个计数信号量，有两个方法acquire()和acquire()，计数信号量由一个指定数量的 "许可" 初始化。每调用一次 acquire()，一个许可会被调用线程取走。每调用一次release()，一个许可会被返还给信号量,因此，在没有任何release()调用时，最多有N个线程能够通过acquire()方法。信号量Semaphore主要有两种用途：1)保护一个重要(代码)部分防止一次超过 N 个线程进入。2) 在两个线程之间发送信号。
* 【使用 ForkJoinPool 进行分叉和合并】：ForkJoinPool在Java7中被引入。它和 ExecutorService很相似，除了一点不同。ForkJoinPool让我们可以很方便地把任务分裂成几个更小的任务，这些分裂出来的任务也将会提交给 ForkJoinPool。任务可以继续分割成更小的子任务，只要它还能分割。