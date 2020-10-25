Java字节码技术

Java byteCode 是由byte的指令

理论上支持256个操作码

理论有4类

1. 操作栈的指令,局部变量的交互
2. 程序流程控制(if else for)
3. 对象操作指令，包括方法调用
4. 算数运算，类型转换指令

我们的计算都在栈上面

变量名，变量的值在本地变量表

Load:

	把变量挪到栈上	 

Store:

	把栈上的值移回本地变量表

JVM运行时的结构

jvm是一台基于栈的虚拟机

每一个线程 都有一个独立于自己的JVM Stack，用于存储

类加载器

类的生命周期

loading :加载，找Class文件

Verification:验证，验证格式、依赖

Preparation:准备,静态字段、方法表

Resolution:解析，符号解析为引用

Initialization：初始化，构造器、静态变量赋值、静态代码块

Using:使用

Unloading:卸载

三种加载器

1. 启动类加载器
   1. 加载jvm基础类的加载
2. 扩展类加载器
   1. 加载jvm扩展类目录中的类
3. 应用类加载器
   1. 自己写的代码

加载器的特点

1. 双亲委托
   1. 自己的代码用到了一个类，查询父加载器中是否有既:
   2. AppClassLoader->ExtClassLoader->BootstrapClassLoader
2. 负载依赖
   1. 加载一个类，需要加载它依赖的类
3. 缓存加载
   1. 类只会被加载器加在一次，会被缓存。

内存模型

1. 每个线程都只能访问自己的线程栈
2. 每个线程都不能访问别的线程的局部变量
3. 所有原生的局部变量都存储在线程栈中，因此对其他线程不可见。
4. 线程可以将一个原生变量的值副本传给另一个线程，但是不能共享其本身
5. 堆内存中包含了Java代码中创建的所有对象。
6. 不管是创建一个对象并将其赋值给另一个对象的成员变量，创建的对象都会被保存到堆内存中。



JVM内存结构

1. 如果原生数据类型的局部变量，那么它的内容难过就全部保留在线程栈上。
2. 如果对象以你用，则栈中的局部变量槽中保存着对象的引用地址，二十几的对象保存在堆中
3. 对象成员、变量本身一起存储在堆上，不管成员变量的类型是原生数值，还是对象引用。
4. 累的静态变量则和类定义一样都保存存在堆中。





JVM 内存整体结构

1. 每启动一个线程，JVM就会在栈空间栈分配对应的线程栈，比如1MB的空间(-Xss1m).
2. 线程栈也叫做Java方法栈，如果使用了JNI方法，则会分配一个单独的本地方法栈(Native Stack)
3. 线程执行过程中，一般会有多个方法组成调用栈（Stack Trace），比如A调用B，B调用C，每次执行一个方法，就会创建对应的栈帧（Frame）

JVM内存参数

1. Xmx
   1. 最小堆内存(Heap的内存)
2. Xms
   1. 最大堆内存(Heap的内存)
3. Xss
   1. tmp





栈帧是一个逻辑上的概念，具体的大小，在方法编译完之后就能确定。

比如返回值 需要有空间存放，每个局部变量都需要对应的地址空间，此外还有给指令使用的操作数栈，以及class指针



JVM堆内存结构

堆内存是所有线程公用的内存空间，JVM将Heap内存为分两类:

1. 年轻代 Young generation 年轻代分为三个内存池
   1. 新生代 Eden space（存活周期短的对象）
   2. 存活区 Survivor space （s0,s1）
      1. S0 和S1一定有一个是空的
      2. 其中一个满了，做一个YoungGC S0 中为零散的碎片，并移到S1中，清空S0 重复Loop
2. 老年代 Old generation(大的对象，或者已经存活很久的对象)

Non-Heap 理论上还是Heap，氛围三个内存池

1. 永久代 Metaspace  元数据区 
2. CCS Compressed Class Space
3. Code Cache 存放JIT编译器编译后的的本地机器代码





CPU和内存行为

CPU的乱序执行

volatile 关键字

原子性操作

内存屏障





什么是JMM

所有的对象，static变量，数组，都必须放到堆内存中。

局部笔亲来那个，方法的入参，异常处理语句的入参不允许在线程之间共享，所以不受内存模型的印象。

多个线程的同时对一个变量访问，进行读写，这个之后只要有某个线程的操作是写，那么就是冲突。

可以背其他线程影响和感知的操作，成为线程的交互

JMM 规范的是线程之间的交互操作，而不管线程内部堆局部变量进行的操作。

JVM的启动参数

 



1. -开头的为标准参数。
2. -D设置系统属性
3. -X为非标准参数，基本都是传给JVM的
4. -XX开头为非稳定参数，专门用于控制JVM的行为。
5. -XX:=+是对bool指进行开关
6. -XX:key = value 的形式，制定某个选项的值

JVM 启动参数--分析诊断

1.  -XX:+-HeapDumpOnOutOfMemoryError 选项, 当 OutOfMemoryError 产生，即内存溢出(堆内存或持久代)时，
2. 自动 Dump 堆内存。
 示例用法: java -XX:+HeapDumpOnOutOfMemoryError -Xmx256m ConsumeHeap
3. -XX:HeapDumpPath 选项, 与 HeapDumpOnOutOfMemoryError 搭配使用, 指定内存溢出时 Dump 文件的目 录。
4. 如果没有指定则默认为启动 Java 程序的工作目录。
 示例用法: java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/ ConsumeHeap
5. 自动 Dump 的 hprof 文件会存储到 /usr/local/ 目录下。
 -XX:OnError 选项, 发生致命错误时(fatal error)执行的脚本。
 例如, 写一个脚本来记录出错时间, 执行一些命令, 或者 curl 一下某个在线报警的 url. 示例用法:java -XX:OnError="gdb - %p" MyApp
 可以发现有一个 %p 的格式化字符串，表示进程 PID。
 -XX:OnOutOfMemoryError 选项, 抛出 OutOfMemoryError 错误
6. 时执行的脚本。 -XX:ErrorFile=filename 选项, 致命错误的日志文件名,绝对路径或者相对路径。 -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1506，远程调试

