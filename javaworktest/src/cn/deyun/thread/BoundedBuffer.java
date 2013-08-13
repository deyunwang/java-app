package cn.deyun.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

	/**
	 * 可阻塞的队列    java5提供的Condition经典例子
	 * 重点:ArrayBlockingQueue 类提供了这项功能，因此没有理由去实现这个示例类
	 * @author Kervin
	 */
public class BoundedBuffer {
   final ReentrantLock lock = new ReentrantLock();
   
   //用一个Condition也能解决，思考为什么需要2个Condition.
   //如果只有一个Condition只能唤醒其中一个，或者通知其中一个。
   final Condition notFull  = lock.newCondition(); 
   final Condition notEmpty = lock.newCondition(); 

   final Object[] items = new Object[100];
   int putptr, takeptr, count;

   public void put(Object x) throws InterruptedException {
     lock.lock();
     try {
       while (count == items.length) 
         notFull.await();
       items[putptr] = x; 
       if (++putptr == items.length) putptr = 0;
       ++count;
       notEmpty.signal();
     } finally {
       lock.unlock();
     }
   }

   public Object take() throws InterruptedException {
     lock.lock();
     try {
       while (count == 0) 
         notEmpty.await();
       Object x = items[takeptr]; 
       if (++takeptr == items.length) takeptr = 0;
       --count;
       notFull.signal();
       return x;
     } finally {
       lock.unlock();
     }
   } 
 }
