package com.lubby.rpc.consumer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.lubby.rpc.bean.TransportMessage;

/**
 * @author Lubby
 * @date 2015年4月22日 下午1:10:59
 *
 */

public class RPCSender {
	//发送地址
	private String address;
	//发送目标端口
	private int port;
	
	//监听端口号
	private int receivePort;

	public RPCSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RPCSender(String address, int port,int receivePort) {
		super();
		this.address = address;
		this.port = port;
		this.receivePort = receivePort;
	}

	public  Object sendAndReceive(TransportMessage callMessageBean) {
		Object result = null;
		try {
			callMessageBean.setCallBackPort(receivePort);
			Socket socket = new Socket(address, port);
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(callMessageBean);

//			ServerSocket serverSocket = new ServerSocket(
//					receivePort);
//			Socket accepteSocket = serverSocket.accept();

			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			result = ois.readObject();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
