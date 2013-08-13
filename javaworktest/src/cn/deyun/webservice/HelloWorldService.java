package cn.deyun.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style=Style.RPC)
public class HelloWorldService {
	
		@WebMethod
		public String sayHello(String name){
			System.out.println(name);
			return "hello," + name;
		}
		
		public static void main(String[] args){
			HelloWorldService hws = new HelloWorldService();
			String url = "http://localhost:8080/HelloWorldService";
			Endpoint.publish(url, hws);
		}
}
