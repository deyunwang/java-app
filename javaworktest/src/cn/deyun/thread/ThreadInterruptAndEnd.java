package cn.deyun.thread;

/**
 * Java中断的线程结束深入探讨
 * 中断:有软中断和硬中断之分。而硬中断多为外围设备引起而且发生多是随机的，故我们在这里只讨论Java程序中的是软中断，是可控的。
 * 这里我必须要回答几个问题，然而是网上大多数文章没有回答的。 a)为什么使用中断? b)何时使用中断? c)中断和结束线程之间有什么关系?
 * d)中断是否就是结束线程
 * 
 * a)在Java中我们引入中断的目的是为了打断线程现在所处的某种状态，但是我们知道这种状态一定是阻塞状态;
 * b)上面已经说了是线程阻塞的时候，我们想要改变它的阻塞的状态，所以通常在线程sleep,wait,join情况下我们可以使用中断;
 * c)由于中断可以捕获，通过这种方式我们可以结束线程。 d)中断不是结束线程，只不过发送了一个中断信号而已，线程要退出还要我们加上自己的结束线程的操作。
 * 
 * 我们现在将中断分两种情况: 可中断的阻塞和不可中断的阻塞
 * 
 * 对于可中断的阻塞情况我们要怎么结束这样的线程呢?下面举个简单的例子
 * 
 * @author Kervin
 * 
 */
public class ThreadInterruptAndEnd {

	public void run1() {
		while (!Thread.currentThread().isInterrupted() /* && more work to do */) {
			try {
				//...
				//sleep(delay);
			} catch (Exception e) {
				Thread.currentThread().interrupt();//重新设置中断标示
			}
		}
	}
	
	
	public void run2() {
		while (!Thread.currentThread().isInterrupted() /* && more work to do */) {
			try {
				//...
				//sleep(delay);
			} catch (Exception e) {
				Thread.currentThread().interrupt();//重新设置中断标示
			}
		}
	}
/**
 * 通过在另外一个线程或者主线程里面调用要结束或者中断线程的句柄执行interrupt（）方法，然后在该线程里面去捕获中断
 * 从而退出run（）方法；当然上面也可以用bool值的方式，在捕获到中断后修改bool值达到退出run方法的目的。
 * 上面的例子都是用于wait，join的情况。
 * 
 * 谈到不可中断的阻塞，由于线程阻塞不可能被中断，或者不具备被中断的条件，所以我们会考虑到一些不同的处理方式： 
 * 一是引入条件可以让该不可中断的线程能够被中断，比如在read的循环里面加个sleep函数，这样就可以捕获中断异常从而达到退出的目的了
 * 二是我们还是可以引入一个valotile的bool值的方式，线程要结束时，在循环里面去修改，注意这里有一个前提是有一个循环，并且循环执行一次的时间很短
 * 三是书上和网上将的情况，socket的处理是用关闭socket的方式也就是关闭底层资源的方式来捕获异常达到退出的目的
 * 四也是网上和书上讲到的对被加锁的块（synchronized方法和临界区）进行中断采用ReentrantLock可以捕获中断
 */
}
