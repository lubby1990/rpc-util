package com.lubby.rpc.client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.lubby.rpc.message.TransportMessage;

/**
 * @author Lubby
 * @date 2015年4月24日 下午3:24:42
 *
 */

public class RPCClient {
	// 服务端地址
	private String serverAddress;
	// 服务端端口
	private int serverPort;

	public RPCClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param serverAddress
	 *            TPC服务地址
	 * @param serverPort
	 *            TPC服务端口
	 * 
	 */
	public RPCClient(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	/**
	 * 同步的请求和接收结果
	 * @param transportMessage
	 * @return
	 */
	public Object sendAndReceive(TransportMessage transportMessage){
		Object result = null;
		try {
			Socket socket = new Socket(serverAddress,serverPort);
			ObjectOutputStream objectOutpusStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutpusStream.writeObject(transportMessage);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			 result = objectInputStream.readObject();
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
	
	

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

}
