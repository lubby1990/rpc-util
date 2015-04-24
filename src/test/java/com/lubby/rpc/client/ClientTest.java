package com.lubby.rpc.client;

import com.lubby.rpc.message.TransportMessage;
import com.lubby.rpc.receiver.RPCServer;

/**
 * @author Lubby
 * @date 2015年4月24日 下午3:48:25
 *
 */

public class ClientTest {
	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
		int serverPort = 4321;
		final RPCClient client = new RPCClient(serverAddress, serverPort);
		final TransportMessage transportMessage = buildTransportMessage();
		for (int i = 0; i < 500; i++) {
			new Thread(new Runnable() {

				public void run() {
					Object result = client.sendAndReceive(transportMessage);
					System.out.println(result);

				}
			}).start();
		}
	}

	private static TransportMessage buildTransportMessage() {

		String interfaceName = "com.lubby.rpc.service.MathService";
		Class[] paramsTypes = { int.class, int.class, String.class };
		Object[] parameters = { 1, 3, "Lubby" };
		String methodName = "getSum";

		TransportMessage transportMessage = new TransportMessage(interfaceName,
				methodName, paramsTypes, parameters);

		return transportMessage;
	}

}
