package com.lubby.rpc.receiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lubby.rpc.message.TransportMessage;

/**
 * @author Lubby
 * @date 2015年4月24日 下午2:41:16
 *
 */

public class RPCServer {
	private int threadSize = 10;
	private ExecutorService threadPool;
	private Map<String, Object> servicePool;
	private int port = 4321;

	public RPCServer() {
		super();
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}
	
	/**
	 * 
	 * @param threadSize
	 *            内部处理线程池大小
	 * @param port
	 *            当前TPC服务的端口号
	 * 
	 */
	
	public RPCServer(int threadSize, int port) {
		this.threadSize = threadSize;
		this.port = port;
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}
	

	/**
	 * 
	 * 
	 * @param servicePool
	 *            装有service对象的Map, Key为全限定接口名,Value为接口实现类对象
	 * @param threadSize
	 *            内部处理线程池大小
	 * @param port
	 *            当前TPC服务的端口号
	 * 
	 */
	public RPCServer(Map<String, Object> servicePool, int threadSize, int port) {
		this.threadSize = threadSize;
		this.servicePool = servicePool;
		this.port = port;
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}

	
	/**
	 * RPC服务端处理函数
	 * 		监听指定TPC端口,每次有请求过来的时候调用服务,放入线程池中处理.
	 * @throws IOException
	 */
	public void service() throws IOException {

		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket receiveSocket = serverSocket.accept();
			final Socket socket = receiveSocket;
			threadPool.execute(new Runnable() {
				
				public void run() {
					try {
						process(socket);
						socket.close();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
		}

	}

	/**
	 * 调用服务
	 * 		通过TCP Socket返回结果对象
	 * 
	 * @param receiveSocket
	 * 			请求Socket
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private void process(Socket receiveSocket) throws IOException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {

		ObjectInputStream objectinputStream = new ObjectInputStream(
				receiveSocket.getInputStream());
		TransportMessage message = (TransportMessage) objectinputStream
				.readObject();

		// 调用服务
		Object result = call(message);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(receiveSocket.getOutputStream());
		objectOutputStream.writeObject(result);
		objectOutputStream.close();
	}

	/**
	 * 服务处理函数 通过包名+接口名在servicePool中找到对应服务 通过调用方法参数类型数组获取Method对象
	 * 通过Method.invoke(对象,参数)调用对应服务
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private Object call(TransportMessage message) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		if (servicePool == null) {
			synchronized (this) {
				servicePool = new HashMap<String, Object>();
			}
		}
		String interfaceName = message.getInterfaceName();
		Object service = servicePool.get(interfaceName);
		Class<?> serviceClass = Class.forName(interfaceName);
		// 检查servicePool中对象,若没有着生产对象
		if (service == null) {
			synchronized (this) {
				service = serviceClass.newInstance();
				servicePool.put(interfaceName, service);
			}
		}
		Method method = serviceClass.getMethod(message.getMethodName(),
				message.getParamsTypes());
		Object result = method.invoke(service, message.getParameters());
		return result;

	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public Map<String, Object> getServicePool() {
		return servicePool;
	}

	public void setServicePool(Map<String, Object> servicePool) {
		this.servicePool = servicePool;
	}

}
