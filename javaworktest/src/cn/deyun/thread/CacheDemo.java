package cn.deyun.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {

	private Map<String, Object> cache = new HashMap<String, Object>();
	
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		
	}
	
	//这个就是一个缓存系统         [经典的缓存系统]   也说明了读写锁的价值
	public Object getData(String key){   //synchronized
		rwl.readLock().lock();
		Object value = null;
		try {
			value = cache.get(key);
			if(value == null){
				rwl.readLock().unlock(); //?思考: 假如三个线程都走到这里     
				rwl.writeLock().lock();
				try {
					if(value == null){
						value = "aaaa";      //实际是去queryDB();
					}
				} 
				finally{
					rwl.writeLock().unlock();
				}
				rwl.readLock().lock();   //恢复读锁
			}
		}
		finally{
			rwl.readLock().unlock();
		}
		
		return value;
	}
	
	/**
	 * java 1.6 API原始缓存系统
	 */
	/*	 class CachedData {
			   Object data;
			   volatile boolean cacheValid;
			   ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
			   void processCachedData() {
			     rwl.readLock().lock();
			     if (!cacheValid) {
			        // Must release read lock before acquiring write lock
			        rwl.readLock().unlock();
			        rwl.writeLock().lock();
			        // Recheck state because another thread might have acquired
			        //   write lock and changed state before we did.
			        if (!cacheValid) {
			          data = ...
			          cacheValid = true;
			        }
			        // Downgrade by acquiring read lock before releasing write lock
			        rwl.readLock().lock();
			        rwl.writeLock().unlock(); // Unlock write, still hold read
			     }
	
			     use(data);
			     rwl.readLock().unlock();
			   }
			 }*/
}
