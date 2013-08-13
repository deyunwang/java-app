package cn.deyun.java7;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OrderNumUtil {
	private static int orderNum = 0001;

	public static String getOrderNo() {
		long No = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		No = Long.parseLong(nowdate) * 10000;// 这里如果一天订单多的话可以用一万或更大
		No += getNo();
		return getUserId() + No;
	}

	public static int getNo() {// 返回当天的订单数+1
		orderNum++;
		return orderNum;
	}

	public static String getUserId() {
		return "100";
	}
	public static String RandomStringId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	public static String getOrderNum() {
		long No = System.currentTimeMillis();
		return "100" + No;
	}
}