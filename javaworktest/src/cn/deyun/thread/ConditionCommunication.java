package cn.deyun.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

	/**
	 * 学习java5中Condition的应用                              
	 * 面试题(这样设计有面向对象的思维)
	 * 子线程循环10次，主线程再循环100次
	 * 又回到子线程循环10次，主线程循环100次
	 * 如此循环50次
	 * @author kervin
	 * @created 2012-11-13 
	 * 总结:要用到共同数据(包括同步锁)的或共同算法若干个方法应该归在同一个类身上，这种设计正好体现了高类聚和程序的健壮性。
	 */
public class ConditionCommunication {

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
	
	
	
	
	//
	/**
	 * 
	 * 加个static，他的作用类似于外部类
	 * Condition.Business: 加个static后类名是这个
	 * Business:           外部类的类名是这个
	 */
	static class Business{
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		private boolean bShouldSub = true;    //是否应该为子类先运行
		/**
		 * 子线程循环10次
		 */
		public  void sub(int i){
			lock.lock();
			try{
				//这个地方用while比if更好，可以查看java api
				//在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。
				//虽然这种情况在实践中很少发生，但是应用程序必须通过以下方式防止其发生，
				//即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中
				while (!bShouldSub) {   //if (bShouldSub == ture)//IBM经典面试题，不能这样写，因为本身就是true			
					try {
						//this.wait();
						condition.await();      //这个地方不能写成condition.wait()
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 10; j++) {
					System.out.println("sub thread sequece of " + j + ",loop of " + i);
				}
				bShouldSub = false;
				//this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。   
				condition.signal();
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
				while(bShouldSub){  
					try {
						//this.wait();
						condition.await();       //让他在等
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for(int j=1;j<=100;j++){
					System.out.println("main thread sequece of " + j + ",loop of " + i);
				}
				bShouldSub = true;
				//notify必须在synchronized中写
				//this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。      
				condition.signal();        //发信号 
			} 
			finally{
				lock.unlock();
			}
		}
	}
}
