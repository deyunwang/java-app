package cn.deyun.java7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Java获取网页源码
 * @author Kervin
 *
 */
public class AccessWebSource {
	public static void main(String[] args) {
		String url = "http://www.2345.com";      // 这里一定要输入'http://'否则会出错
		System.out.println(getHTML(url, "gbk")); // 使用原网页里声明的gb2312反而会出现乱码
	}
	public static String getHTML(String pageURL,String encoding){
		StringBuilder pageHTML = new StringBuilder();
		try {
			URL url = new URL(pageURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", "MSIE 7.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHTML.toString();
	}
}
