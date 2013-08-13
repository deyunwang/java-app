package cn.deyun.concurrent;
/**
 * 深入分析Volatile的实现原理
 * @author Kervin引用淘宝资深开发工程师方腾飞博客
 * 在多线程并发编程中synchronized和Volatile都扮演着重要的角色，Volatile是轻量级的synchronized
 * 在多线程并发编程中synchronized和Volatile都扮演着重要的角色，Volatile是轻量级的synchronized
 * 可见性的意思是当一个线程修改一个共享变量时，另外一个线程能读到这个修改的值。
 * 它在某些情况下比synchronized的开销更小
 * 专业术语定义:
 * 共享变量:在多个线程之间能够被共享的变量被称为共享变量。包括所有的实例变量，静态变量和数组元素。他们都被存放在堆内存中，Volatile只作用于共享变量。
 * 内存屏障:(Memory Barriers)是一组处理器指令，用于实现对内存操作的顺序限制。
 * 缓冲行:(Cache line)缓存中可以分配的最小存储单位。处理器填写缓存线时会加载整个缓存线，需要使用多个主内存读周期。
 * 原子操作:(Atomic operations)不可中断的一个或一系列操作。
 * 缓存行填充:(cache line fill)当处理器识别到从内存中读取操作数是可缓存的，处理器读取整个缓存行到适当的缓存（L1，L2，L3的或所有）
 * 缓存命中:(cache hit)如果进行高速缓存行填充操作的内存位置仍然是下次处理器访问的地址时，处理器从缓存中读取操作数，而不是从内存。
 * 写命中:(write hit)当处理器将操作数写回到一个内存缓存的区域时，它首先会检查这个缓存的内存地址是否在缓存行中，如果不存在一个有效的缓存行，则处理器将这个操作数写回到缓存，而不是写回到内存，这个操作被称为写命中。
 * 写缺失:(write misses the cache)一个有效的缓存行被写入到不存在的内存区域
 * Java语言规范第三版中对volatile的定义:
 * java编程语言允许线程访问共享变量，为了确保共享变量能被准确和一致的更新，线程应该确保通过排他锁单独获得这个变量。
 * Java语言提供了volatile，在某些情况下比锁更加方便。如果一个字段被声明成volatile，java线程内存模型确保所有线程看到这个变量的值是一致的
 * 为什么要使用Volatile:
 * Volatile变量修饰符如果使用恰当的话，它比synchronized的使用和执行成本会更低，因为它不会引起线程上下文的切换和调度。
 * Volatile的实现原理:
 * 
 */
public class VolatileTest {

}
