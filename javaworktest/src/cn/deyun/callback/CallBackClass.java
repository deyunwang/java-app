package cn.deyun.callback;

public class CallBackClass implements ICallBack {

	@Override
	public void run() {
		//输出当前时间
		System.out.println(System.currentTimeMillis());
	}
}
