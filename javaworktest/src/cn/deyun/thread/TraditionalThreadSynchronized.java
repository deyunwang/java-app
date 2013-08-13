package cn.deyun.thread;


/**
 * 传统线程互斥技术
 * @version 1.0
 * @author kervin
 * @created 2012-11-9
 */
public class TraditionalThreadSynchronized {
	
	
	public static void main(String[] args) {
		new TraditionalThreadSynchronized().init();
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
					outputer.output2("liming");
				}
			}
		}).start();
	} 
	
	/**
	 * 输出字符串
	 */
	static class Outputer{
//		byte[] b = new byte[0];           //最便宜的互斥
		public void output(String name){
			int len = name.length();
			//这里如果不是this，就不能和下面一个方法进行互斥
			synchronized (Outputer.class) {      //互斥必须使用同一个对象   由于可以针对任意代码块，且可任意指定上锁的对象，故灵活性较高。
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
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

