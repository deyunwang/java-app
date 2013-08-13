package cn.deyun.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableAndFuture {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		//如果没有返回值就用  threadPool.execute(command)
		
		Future<String> future = 
				
		threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "hello";
			}
		});  
		
		System.out.println("等等结果");
		try {
//			System.out.println(future.get());
			System.out.println("拿到结果: "+future.get(1,TimeUnit.SECONDS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for(int i =1;i<=10;i++){
			final int seq = i;
			completionService.submit(new Callable<Integer>() {
				
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}
		for(int i =0;i<10;i++){
			try {
				System.out.println(completionService.take().get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
