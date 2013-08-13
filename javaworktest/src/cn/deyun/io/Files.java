package cn.deyun.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.SequenceInputStream;

import org.junit.Test;

public class Files {
	
	/**
	 * 读取磁盘文件
	 * @param filePath	文件路径
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		String lineTxt = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {      // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineTxt;
	}
	
	/**
	 * SequenceInputStream主要用来将2个流合并在一起，比如将两个txt中的内容合并为另外一个txt
	 * @throws Exception
	 */
	@Test
	public void SequenceInputStreamDemo() throws Exception{
		File file1 = new File("d:" + File.separator + "hello1.txt");
		File file2 = new File("d:" + File.separator + "hello2.txt");
		File file3 = new File("d:" + File.separator + "hello.txt");
		InputStream input1 = new FileInputStream(file1);
		InputStream input2 = new FileInputStream(file2);
		OutputStream output = new FileOutputStream(file3);
		// 合并流
		SequenceInputStream sis = new SequenceInputStream(input1, input2);
		int temp = 0;
		while ((temp = sis.read()) != -1) {
			output.write(temp);
		}
		input1.close();
		input2.close();
		output.close();
		sis.close();
	}
}
