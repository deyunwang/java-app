package cn.deyun.thread;

/**
 * 线程封装类
 * @author kervin
 * @created 2012-11-13
 */
public class MyThread {

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
