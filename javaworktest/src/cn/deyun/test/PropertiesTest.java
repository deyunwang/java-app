package cn.deyun.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.junit.Test;


public class PropertiesTest {
	
	@Test		
	public void Sample() throws Exception{
		System.out.println(getUri());
	}
	
	public String getPropertie(Properties p,String key) throws Exception {
		FileInputStream fis = new FileInputStream("config.properties");
		p.load(fis);
		fis.close();
		return p.getProperty(key);
	}
	public void setPropertie(Properties p,String key,String value) throws Exception {
		FileOutputStream fos = new FileOutputStream("config.properties");
		p.store(fos,null);
		p.setProperty(key, value);
		fos.close();
	}
	public String getUri() throws Exception{
		StringBuilder sb = new StringBuilder();
		Properties p = new Properties(); // 属性集合对象
		sb.append(getPropertie(p, "httptype"));
		sb.append(getPropertie(p, "host"));
		sb.append("/");
		sb.append(getPropertie(p, "firstCategory"));
		return sb.toString();
	}
}
