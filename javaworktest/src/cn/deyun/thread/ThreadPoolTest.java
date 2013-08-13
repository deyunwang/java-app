package cn.deyun.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池学习 问题：如何实现线程死掉后重新启动[答案:Executors.newSingleThreadExecutor()]
 * @author Kervin
 */
public class ThreadPoolTest {
	public static void main(String[] args) {
		//ExecutorService threadPool = Executors.newFixedThreadPool(3);   //固定的线程池
		
//		ExecutorService threadPool = Executors.newCachedThreadPool();     //内部的线程是固定的,当服务不过来的时候，会自动增加新的线程来处理。
		
		ExecutorService threadPool = Executors.newSingleThreadExecutor(); //跟单个线程一样的，与线程池没什么概念，这个好处是如果这个线程死了，他就会再new一个新的线程。始终保持单线程。
		// 线程池中的每个任务都是Runnable
		for(int i=1;i<=10;i++){
			final int task = i;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for(int j=1;j<=10;j++){
						try {
							Thread.sleep(20);
							System.out.println(Thread.currentThread().getName()
									+ " is looping of " + j + " for task"
									+ task);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			});      
		}
		System.out.println("all of 10 tasks have committed! ");
		
		threadPool.shutdown();       //所用的任务都做完了，就关闭，释放资源。
		//threadPool.shutdownNow();    //不管任务有没有做完，都关闭线程池，释放资源。
		
		
		
		//定时器线程任务
/*		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("bombing!");
				
			}
		}, 10, TimeUnit.SECONDS);*/
		
		
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("bombing!");
				
			}
		}, 6,2, TimeUnit.SECONDS);
		
	}
}
