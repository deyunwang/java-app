package cn.deyun.thread;

/**
 * 多线程访问同一个数据，如果不进行同步会导致错误结果
 * 
 * @author Kervin
 * 
 */
public class MultiThreadAccessVariable {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Runnable addit = new AddOne();
			new Thread(addit).start();
		}
	}
}
/*楼主这里的测试手段是:
    System.out.println(Thread.currentThread().getName() + ",before:" + cnt);
    cnt++;
    System.out.println(Thread.currentThread().getName() + ",after:" + cnt);
这三行代码转换成字节码指令不知道成为多少行了,线程之间互相调度执行结果很难预测,所以楼主这个测试程序本身就没有什么可行性,试图从里面分析出规律是不可行的*/
class AddOne implements Runnable {
	static int cnt = 0;
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ",before:" + cnt);
		cnt++;
		System.out.println(Thread.currentThread().getName() + ",after:" + cnt);
	}
}
