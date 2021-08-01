# 本工程的作用：
> 本工程用于展示JVM体系中常见知识点(复现、示例代码、解释)。




# GC知识介绍
* `新生代`几种垃圾收集方式：
    * Serial (复制) 是一种stop-the-world(导致应用全部暂停，僵死一会儿), 使用单个GC线程进行复制收集。
      将幸存对象从 Eden复制到幸存 Survivor空间，并且在幸存Survivor空间之间复制，直到它决定这些对象已经足够长了，在某个点一次性将它们复制到旧生代old generation.
    * Parallel Scavenge (PS Scavenge)是一种stop-the-world, 使用多个GC线程实现复制收集。如同上面复制收集一样，但是它是并行使用多个线程。
    * ParNew是一种stop-the-world, 使用多个GC线程实现的复制收集，区别于"Parallel Scavenge"在于它与CMS可搭配使用，它也是并行使用多个线程，内部有一个回调功能允许旧生代操作它收集的对象。


