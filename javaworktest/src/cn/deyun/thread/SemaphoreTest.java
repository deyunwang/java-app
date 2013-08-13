package cn.deyun.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
	
	/**
	 * Semaphore(信号灯)的应用学习
	 * Semaphore可以维护当前访问自身的线程个数,并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数
	 * 例如，实现一个文件允许的并发访问数。
	 * Semaphore可以按照先后的顺序获得机会，这取决于构造Semaphore对象时传入的参数选项。
	 * 可以用来做Android客户端多线程断点续传.
	 * @author Kervin
	 */
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);        //3个信号灯
		for(int i=0;i<10;i++){
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						sp.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程"+Thread.currentThread().getName()+
							"进入，当前已有" + (3-sp.availablePermits())+"个并发");
					
					try {
						Thread.sleep((long)Math.random()*10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName()
							+ "即将离开");
					sp.release();   //当有人离开，他就释放灯
					//下面代码有时候执行不准确，因为其没有和上面的代码合成原子
					System.out.println("线程"+Thread.currentThread().getName()+
							"进入，当前已有" + (3-sp.availablePermits())+"个并发");
				}
			};
			service.execute(runnable);
		}
		
	}
}

// jdk1.6 API 实例代码
/*class Pool {
	   private static final int MAX_AVAILABLE = 100;
	   private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

	   public Object getItem() throws InterruptedException {
	     available.acquire();
	     return getNextAvailableItem();
	   }

	   public void putItem(Object x) {
	     if (markAsUnused(x))
	       available.release();
	   }

	   // Not a particularly efficient data structure; just for demo

	   protected Object[] items = ... whatever kinds of items being managed
	   protected boolean[] used = new boolean[MAX_AVAILABLE];

	   protected synchronized Object getNextAvailableItem() {
	     for (int i = 0; i < MAX_AVAILABLE; ++i) {
	       if (!used[i]) {
	          used[i] = true;
	          return items[i];
	       }
	     }
	     return null; // not reached
	   }

	   protected synchronized boolean markAsUnused(Object item) {
	     for (int i = 0; i < MAX_AVAILABLE; ++i) {
	       if (item == items[i]) {
	          if (used[i]) {
	            used[i] = false;
	            return true;
	          } else
	            return false;
	       }
	     }
	     return false;
	   }

	 }*/

