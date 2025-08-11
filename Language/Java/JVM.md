# JVM

![JVM结构](images/JVM.png)

## 垃圾收集器

使用 java -XX:+PrintCommandLineFlags -version 命令查看jdk默认垃圾收集器。

JDK8：Parallel Scavenge（新生代）+ Parallel Old（老年代）
JDK9-22：G1

### Serial收集器

单线程收集器，只会使用一条垃圾收集线程去完成垃圾收集工作，在进行垃圾回收时必须暂停所有的工作线程（”Stop The World“），直到收集结束。

新生代采用标记-复制算法，老年代采用标记-整理算法。

### ParNew收集器

ParNew收集器是Serial收集器的多线程版本，除了使用多线程进行垃圾收集外，其余行为（控制参数、收集算法、回收策略等）和Serial收集器完全一样。

### Parallel Scavenge收集器

P