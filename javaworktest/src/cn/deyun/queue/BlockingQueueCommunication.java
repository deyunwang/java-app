package cn.deyun.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

	/**
	 * 例:用两个1个空间的队列来实现同步通知的功能
	 * BlockingQueue学习:用可阻塞队列实现同步通信技术
	 * 面试题(这样设计有面向对象的思维)
	 * 子线程循环10次，主线程再循环100次
	 * 又回到子线程循环10次，主线程循环100次
	 * 如此循环50次
	 * @version 1.0
	 * @author kervin
	 * @created 2012-11-13 
	 * 总结:要用到共同数据(包括同步锁)的或共同算法若干个方法应该归在同一个类身上，这种设计正好体现了高类聚和程序的健壮性。
	 */
public class BlockingQueueCommunication {

	public static void main(String[] args) {
		
		final Business business = new Business();
		
		new Thread(new Runnable() {
			
		/**
		 * 子线程	
		 */
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();
		
		/**
		 * 主线程
		 */
		
		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}
	}
	/**
	 * 相当于类部的外部类
	 * @author Kervin
	 */
	static class Business{
		BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
		BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
		
		{
			try {
				/**  这个叫匿名构造方法:这个必须要在构造方法里面给他赋值     */
				queue2.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 子线程循环10次
		 */
		public void sub(int i){
			try {
				queue1.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("sub thread sequece of " + j + ",loop of " + i);
			}
			try {
				queue2.take();               
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 主线程循环100次
		 */
		public void main(int i){
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int j=1;j<=100;j++){
				System.out.println("main thread sequece of " + j + ",loop of " + i);
			}
			try {
				queue1.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

