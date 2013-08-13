package cn.deyun.nio;import java.io.FileInputStream;import java.io.FileOutputStream;import java.io.InputStream;import java.io.OutputStream;import java.nio.ByteBuffer;import java.nio.channels.FileChannel;/** * 对比性能 - 没有发现nio有多快 * @author <a href="mailto:wangjian_me@126.com">王健</a> * @version 1.0 2011-10-28 */public class FileCopy {	public static void main(String[] args) throws Exception {		long sum = 0;						for(int i=0;i<10;i++){			long time = System.currentTimeMillis();			FileChannel src = new FileInputStream("d:/a/1.avi").getChannel();			FileChannel dest = new FileOutputStream("d:/a/a2"+i+".avi").getChannel();			ByteBuffer bb = ByteBuffer.allocate(1024); //分配内存			while(src.read(bb)>0){				bb.flip();//将极限设置为位置值，再将位置设置为0				dest.write(bb);				bb.clear();//将容量设置为极限值，再将位置设置为0			}			src.close();			dest.close();			time = System.currentTimeMillis()-time;			sum=sum+time;			System.err.println("用时："+time);		}		sum = sum/10;		System.err.println("NIO平均用时："+sum);								for(int i=0;i<10;i++){			long time = System.currentTimeMillis();			InputStream  in  = new FileInputStream("d:/a/1.avi");			OutputStream out = new FileOutputStream("d:/a/a"+i+".avi");			byte[] b = new byte[1024];			int len = 0;			while((len=in.read(b))!=-1){				out.write(b,0,len);			}			in.close();			out.close();			time = System.currentTimeMillis()-time;			sum=sum+time;			System.err.println("用时："+time);		}		sum = sum/10;		System.err.println("OIO平均用时："+sum);											}}