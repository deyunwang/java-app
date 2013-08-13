package cn.deyun.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

	/**
	 * 学习java5中Condition的应用        
	 * 三个Condition通信                      
	 * 面试题(这样设计有面向对象的思维)
	 * 子线程(老2)循环10次，另一子线程循环20次（老3）主线程再循环100次
	 * 又回到子线程循环20次，另一子线程循环20次（老3），主线程循环100次
	 * 如此循环50次
	 * @author kervin
	 * @created 2012-11-13 
	 * 总结:要用到共同数据(包括同步锁)的或共同算法若干个方法应该归在同一个类身上，这种设计正好体现了高类聚和程序的健壮性。
	 */
public class ThreeConditionCommunication {

	public static void main(String[] args) {
		
		final Business business = new Business();
		
		new Thread(new Runnable() {
			
		/**
		 * 子线程1	
		 */
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub2(i);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
		/**
		 * 子线程2
		 */
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub3(i);
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
	
	
	
	
	//
	/**
	 * 
	 * 加个static，他的作用类似于外部类
	 * Condition.Business: 加个static后类名是这个
	 * Business:           外部类的类名是这个
	 */
	static class Business{
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		private int shouldSub = 1;                 //代表老大先走
		/**
		 * 子线程循环10次
		 */
		public  void sub2(int i){
			lock.lock();
			try{
				//这个地方用while比if更好，可以查看java api
				//在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。
				//虽然这种情况在实践中很少发生，但是应用程序必须通过以下方式防止其发生，
				//即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中
				while (shouldSub != 2) {   //if (bShouldSub == ture)//IBM经典面试题，不能这样写，因为本身就是true			
					try {
						//this.wait();
						condition2.await();      //这个地方不能写成condition.wait()
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 10; j++) {
					System.out.println("sub2 thread sequece of " + j + ",loop of " + i);
				}
				shouldSub = 3;             //该老3走
				//this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。   
				condition3.signal();
			}
			finally{
				lock.unlock();
			}
		}
		
		
		/**
		 * 子线程循环10次
		 */
		public  void sub3(int i){
			lock.lock();
			try{
				//这个地方用while比if更好，可以查看java api
				//在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。
				//虽然这种情况在实践中很少发生，但是应用程序必须通过以下方式防止其发生，
				//即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中
				while (shouldSub != 3) {   //if (bShouldSub == ture)//IBM经典面试题，不能这样写，因为本身就是true			
					try {
						//this.wait();
						condition3.await();      //这个地方不能写成condition.wait()
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 20; j++) {
					System.out.println("sub3 thread sequece of " + j + ",loop of " + i);
				}
				shouldSub = 1;
				//this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。   
				condition1.signal();
			}
			finally{
				lock.unlock();
			}
		}
		
		
		
		/**
		 * 主线程循环100次
		 */
		public  void main(int i){
			lock.lock();
			try {
				while(shouldSub != 1){  
					try {
						//this.wait();
						condition1.await();       //让他在等
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for(int j=1;j<=100;j++){
					System.out.println("main thread sequece of " + j + ",loop of " + i);
				}
				shouldSub = 2;
				//notify必须在synchronized中写
				//this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。      
				condition2.signal();        //发信号 
			} 
			finally{
				lock.unlock();
			}
		}
	}
}
