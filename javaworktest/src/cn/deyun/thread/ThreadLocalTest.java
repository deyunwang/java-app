package cn.deyun.thread;

import java.util.Random;

/**
 * ThreadLocal:这个类就相当于一个Map
 * ThreadLocal为我们解决多线程程序的并发问题提供了一种新的思路。使用这个工具类可以很简洁地编写出优美的多线程程序。
 * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本。
 * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
 * 实现原理:ThreadLocal是如何做到为每一个线程维护变量的副本的呢？
 * 其实实现的思路很简单：在ThreadLocal类中有一个Map，用于存储每一个线程的变量副本
 * Map中元素的键为线程对象，而值对应线程的变量副本
 * 线程范围内的共享数据
 * @author kervin
 * @created 2012-11-14 
 */
public class ThreadLocalTest {

	static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	public static void main(String[] args) {
		
		for(int i=0;i<2;i++){                  //启动两个线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data :" + data);
					x.set(data);               //可以理解这个数据存到当前线程里面去了
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	/**
	 * A模块
	 */
	static class A{
		public void get(){
			int data = x.get();              //取该线程存进来的时候数据
			System.out.println("A from " + Thread.currentThread().getName()
					+ " get data :" + data);
		}
	}
	/**
	 * B模块
	 */
	static class B{
		public void get(){
			int data = x.get();
			System.out.println("B from " + Thread.currentThread().getName()
					+ " get data :" + data);
		}
	}
}


/*
      我们自己就可以提供一个简单的实现版本
  public class ThreadLocal{ 
   　private Map values =Collections.synchronizedMap(newHashMap());　 
     public Object get(){ 
　           Thread curThread = Thread.currentThread(); 
　           Object o = values.get(curThread); 
　           if (o == null||!values.containsKey(curThread)){ 
　                 o = initialValue(); 
                　 values.put(curThread, o); 
             } 
　           return o;　 
    } 


    public void set(Object newValue){ 
    　　 values.put(Thread.currentThread(), newValue); 
    } 


   　public Object  initialValue(){ 
　        return null; 
     } 
} */
