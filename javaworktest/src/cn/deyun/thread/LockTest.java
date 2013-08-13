package cn.deyun.thread;

import java.util.concurrent.locks.ReentrantLock;


/**
 * 锁比传统的synchnoized更加面向对象,他还包括读写锁
 * 读写锁:分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥，写锁与写锁互斥。这是由jvm自己控制的，你只要上好相应的锁即可
 * @version 1.0
 * @author kervin
 * @created 2012-11-9
 */
public class LockTest {
	
	
	public static void main(String[] args) {
		new LockTest().init();
	}
	
	public void init(){
		
		/**
		 * 创建一个线程打印wangdeyun
		 */
		
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					MyThread.sleep(10);
					outputer.output("zhangxiaoxiang");
				}
			}
		}).start();
		/**
		 * 创建一个线程打印liming
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					MyThread.sleep(10);
					outputer.output("wangdeyun");
				}
			}
		}).start();
	} 
	
	/**
	 * 输出字符串
	 */
	static class Outputer{
		final ReentrantLock lock = new ReentrantLock();   //new一个可再进入的锁
		public void output(String name){
			int len = name.length();
			lock.lock();
			try {
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			} 
			finally{
//				/** 这个方法一定要放在finally里面。不然锁被锁了，不能再打开了。*/
				lock.unlock();
			}
		}

		
		
		/**
		 * 另外一种互斥方法
		 * 代表这个方法加锁,相当于不管哪一个线程（例如线程A），运行到这个方法时,都要检查有没有其它线程B（或者C、 D等）正在用这个方法
		 * 有的话要等正在使用synchronized方法的线程B（或者C 、D）运行完这个方法后再运行此线程A,没有的话,直接运行。
		 * 它包括两种用法：synchronized 方法和 synchronized 块
		 * synchronized 方法的缺陷：若一个大的方法声明synchronized 将大大影响效率
		 * 典型地，若将线程类的方法 run() 声明为 synchronized
		 * 由于在线程的整个生命期内它一直在运行，因此将导致它对本类任何 synchronized 方法的调用都永远不会成功。
		 * 当然我们可以通过将访问类成员变量的代码放到专门的方法中，将其声明为 synchronized
		 * 并在主方法中调用来解决这一问题，但是 Java 为我们提供了更好的解决办法，那就是 synchronized 块。
		 */
		public synchronized void output2(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		/**
		 * 
		 * 这个方法不能与output(),output1(),同步
		 * 如果要实现同步，必须把output()中的synchronized (this),this改成Outputer.class
		 * 类的字节码在内存中也算一个对象
		 * 这样output()就和output3()已经同步了。
		 */
		public static synchronized void output3(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
}

