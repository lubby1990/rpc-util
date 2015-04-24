package com.lubby.rpc.producer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.lubby.rpc.bean.TransportMessage;

/**
 * @author Lubby
 * @date 2015年4月22日 下午1:40:44
 *
 */

public class RPCReceiver {
	private int port = 12345;
	Map<String, Object> servicePool = new HashMap<String, Object>();
	{
		servicePool.put("com.lubby.rpc.service.MathService", new com.lubby.rpc.service.MathService());
	}

	public RPCReceiver() {
		super();
	}

	public RPCReceiver(int port) {
		super();
		this.port = port;
	}

	public void process() {
		Object result = null;
		Socket socket = null;
		ObjectOutputStream oos = null;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
				socket = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				TransportMessage callMessageBean = (TransportMessage) ois
						.readObject();

				result = call(callMessageBean);

				 oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(result);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 调用方法获取结果
	public Object call(TransportMessage callMessageBean)
			throws ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String interfaceName = callMessageBean.getInterfaceName();
		Class serviceClass = Class.forName(interfaceName);
		Object service = servicePool.get(callMessageBean.getInterfaceName());
		Method method = serviceClass.getMethod(callMessageBean.getMethodName(), callMessageBean.getParamsTypes());
		Object result = method.invoke(service, callMessageBean.getArguments());

		return result;
	}

}
