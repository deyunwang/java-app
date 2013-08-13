package cn.deyun.thread;

	/**
	 * 传统线程同步通信技术
	 * 面试题(这样设计有面向对象的思维)
	 * 子线程循环10次，主线程再循环100次
	 * 又回到子线程循环10次，主线程循环100次
	 * 如此循环50次
	 * @author kervin
	 * @created 2012-11-13 
	 * 总结:要用到共同数据(包括同步锁)的或共同算法若干个方法应该归在同一个类身上，这种设计正好体现了高类聚和程序的健壮性。
	 */
public class TraditionalThreadCommunication {

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
}
class Business{
	private boolean bShouldSub = true;    //是否应该为子类先运行
	/**
	 * 子线程循环10次
	 */
	public synchronized void sub(int i){
		//这个地方用while比if更好，可以查看java api
		//在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。
		//虽然这种情况在实践中很少发生，但是应用程序必须通过以下方式防止其发生，
		//即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中
		while (!bShouldSub) {   //if (bShouldSub == ture)//IBM经典面试题，不能这样写，因为本身就是true			
			try {
				this.wait();    //wait要放在 synchronized里面
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= 10; j++) {
			System.out.println("sub thread sequece of " + j + ",loop of " + i);
		}
		bShouldSub = false;
		this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。       
	}
	/**
	 * 主线程循环100次
	 */
	public synchronized void main(int i){
		while(bShouldSub){  
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(int j=1;j<=100;j++){
			System.out.println("main thread sequece of " + j + ",loop of " + i);
		}
		bShouldSub = true;
		this.notify();           //如果子线程执行完了，主线程在等待，这个时候应该唤醒它。       
	}
}




















