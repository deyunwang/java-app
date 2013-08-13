package cn.deyun.callback;
import java.util.Scanner;

public class Controller {
	
	public ICallBack CallBackObject = null;  //引用回调对象
	Scanner input = new Scanner(System.in);  //读取命令行输入
	
	//构造方法
	public Controller(ICallBack obj) {
		this.CallBackObject = obj;
	}
	
	public void Begin(){
		while(input.next() != null){         //判断是否有输入
			CallBackObject.run();
		}
	}
	
	public static void main(String[] args) {
		
		//创建控制器对象，将提供给它的回调对象传入
		Controller obj = new Controller(new CallBackClass());
		//启动控制器对象运行
		obj.Begin();
	}
}
