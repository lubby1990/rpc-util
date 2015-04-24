package com.lubby.rpc.client;

import java.lang.reflect.Method;

import com.lubby.rpc.bean.TransportMessage;
import com.lubby.rpc.consumer.RPCSender;
import com.lubby.rpc.service.MathService;

/**
 * @author Lubby
 * @date 2015年4月22日 下午4:15:49
 *
 */

public class Client {

	public static void main(String[] args) {
		final Client client = new Client();
		for(int i=0;i<10;i++){
			final int a = i;
			new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					client.send(a, 2*a);
				}
			}).start();
			
		}
	}
	
	public  void send(int i ,int j){
		RPCSender caller = new RPCSender("127.0.0.1", 12345, 54321);

		String interfaceName = "com.lubby.rpc.service.MathService";
		Method method = null;
		Object[] parameters = { i, j,"Lubby" };
		String methodName = "getSum";
		Class[] paramsTypes = { int.class, int.class, String.class };
		TransportMessage message = new TransportMessage(interfaceName,
				methodName, paramsTypes, parameters, 4321);

		Object result = caller.sendAndReceive(message);
		System.out.println(result);
	}

}
