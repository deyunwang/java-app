package cn.deyun.thread;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
	/**
	 * 传统定时期技术回顾(首先按2秒钟炸，再按4秒炸，再按2秒炸...如此循环)
	 * @version 1.0                 定义周一至周五的时候做什么事用开源的工具:Quaitz
	 * @author kervin
	 * @created 2012-11-13
	 * schedule(TimerTask task, Date firstTime, long period):可以实现按指定时间收邮件，然后每隔24小时一次
	 */
public class TraditionalTimerTest {
	
	static int count = 0;
	public static void main(String[] args) {
		
		class MyTimerTask extends TimerTask{
			@Override
			public void run() {
				count += (count+1)%2; 
				//新开一个新程
				System.out.println("bombing");
				new Timer().schedule(new MyTimerTask(), 2000+2000*count);
			}
		}
		
		
		
		/**
		 * 一个定时器
		 */
		new Timer().schedule(new MyTimerTask(), 2000);  
		
		while(true){
			System.out.println(Calendar.getInstance().get(Calendar.SECOND));
			MyThread.sleep(1000);
		}
	}
}
