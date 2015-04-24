package com.lubby.rpc.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.lubby.rpc.receiver.RPCServer;
import com.lubby.rpc.service.impl.MathServiceImpl;

/**
 *     @author Lubby
 *     @date 2015年4月24日  下午3:48:32
 *
 */

public class ServerTest {
	
	public static void main(String[] args){
		Map<String,Object> servicePool = new  HashMap<String, Object>();
		servicePool.put("com.lubby.rpc.service.MathService", new MathServiceImpl());
		RPCServer server = new RPCServer(servicePool,4, 4321);
		try {
			server.service();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
