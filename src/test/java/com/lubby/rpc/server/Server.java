package com.lubby.rpc.server;

import com.lubby.rpc.producer.RPCReceiver;

/**
 *     @author Lubby
 *     @date 2015年4月22日  下午4:16:18
 *
 */

public class Server {
	public static void main(String[] args){
		RPCReceiver receiver = new RPCReceiver(12345);
		while(true){
			receiver.process();
		}
		
	}

}
