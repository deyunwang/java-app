package cn.deyun.thread;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

	/**
	 * java5读写锁技术的妙用
	 * @author Kervin
	 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final Queue3 q3 = new Queue3();
		for(int i=0;i<3;i++){
			new Thread(){
				@Override
				public void run() {
					q3.get();
				};
			}.start();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						q3.put(new Random().nextInt(10000));
					}
				}
			}).start();
		}
	}
}
class Queue3{                       //读和写要互斥 
	private Object data = null;     //共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据
	//创建一个读写锁
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public void get(){
		rwl.readLock().lock();   //读锁
		try {
			System.out.println(Thread.currentThread().getName()+" be ready to read data!");
			Thread.sleep((long)Math.random()*1000);
			System.out.println(Thread.currentThread().getName()+" have read data: " + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//不管程序出现什么意外，锁还是要释放
			rwl.readLock().unlock();
		}
	}
	
	public void put(Object data){
		rwl.writeLock().lock();  //写锁
		try {
			System.out.println(Thread.currentThread().getName() + " be ready to write data!");
			Thread.sleep((long)Math.random()*1000);
			this.data = data;
			System.out.println(Thread.currentThread().getName() + " be have read data :" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			rwl.writeLock().unlock();
		}
	}
}