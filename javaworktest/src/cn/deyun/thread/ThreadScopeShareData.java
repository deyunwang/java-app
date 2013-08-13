package cn.deyun.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 传统线程范围内的共享数据
 * @author kervin
 * @created 2012-11-14 
 */
public class ThreadScopeShareData {

//	private static int data = 0;
	private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();
	public static void main(String[] args) {
		
		for(int i=0;i<2;i++){                  //启动两个线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data :" + data);
					threadData.put(Thread.currentThread(), data);
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
			int data = threadData.get(Thread.currentThread());
			System.out.println("A from " + Thread.currentThread().getName()
					+ " get data :" + data);
		}
	}
	/**
	 * B模块
	 */
	static class B{
		public void get(){
			int data = threadData.get(Thread.currentThread());
			System.out.println("B from " + Thread.currentThread().getName()
					+ " get data :" + data);
		}
	}

}
