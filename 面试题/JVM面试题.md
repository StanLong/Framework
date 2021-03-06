**JVM垃圾回收机制，何时触发MinorGC等操作**

分代垃圾回收机制：不同的对象生命周期不同。把不同生命周期的对象放在不同代上，不同代上采用最合适它的垃圾回收方式进行回收。 
JVM中共划分为三个代：年轻代、年老代和持久代， 
年轻代：存放所有新生成的对象； 
年老代：在年轻代中经历了N次垃圾回收仍然存活的对象，将被放到年老代中，故都是一些生命周期较长的对象； 
持久代：用于存放静态文件，如Java类、方法等。 
新生代的垃圾收集器命名为“minor gc”，老生代的GC命名为”Full Gc 或者Major GC”.其中用System.gc()强制执行的是Full Gc. 
判断对象是否需要回收的方法有两种： 
1.引用计数 
当某对象的引用数为0时，便可以进行垃圾收集。 
2.对象引用遍历 
果某对象不能从这些根对象的一个（至少一个）到达，则将它作为垃圾收集。在对象遍历阶段，gc必须记住哪些对象可以到达，以便删除不可到达的对象，这称为标记（marking）对象。

触发GC（Garbage Collector）的条件： 
1)GC在优先级最低的线程中运行，一般在应用程序空闲即没有应用线程在运行时被调用。 
2)Java堆内存不足时，GC会被调用