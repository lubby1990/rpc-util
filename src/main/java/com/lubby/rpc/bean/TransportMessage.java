package com.lubby.rpc.bean;

import java.io.Serializable;

/**
 * @author Lubby
 * @date 2015年4月22日 下午1:06:18
 *
 */

public class TransportMessage implements Serializable {

	private String interfaceName;
	private String methodName;
	private Class[] paramsTypes;
	private Object[] arguments;
	private int callBackPort = 4321;

	public TransportMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransportMessage(String interfaceName, String methodName,
			Class[] paramsTypes, Object[] arguments, int callBackPort) {
		super();
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.paramsTypes = paramsTypes;
		this.arguments = arguments;
		this.callBackPort = callBackPort;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamsTypes() {
		return paramsTypes;
	}

	public void setParamsTypes(Class[] paramsTypes) {
		this.paramsTypes = paramsTypes;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public int getCallBackPort() {
		return callBackPort;
	}

	public void setCallBackPort(int callBackPort) {
		this.callBackPort = callBackPort;
	}

}
