package com.lubby.rpc.message;

import java.io.Serializable;

/**
 * @author Lubby
 * @date 2015年4月22日 下午1:06:18
 *	远程调用信息封装.
 *	包括    1.调用接口名称  (包名+接口名)   2.调用方法名  3.调用参数Class类型数组  4.调用接口的参数数组
 */
public class TransportMessage implements Serializable {
	//包名+接口名称  如com.lubby.rpc.service.MathService.
	private String interfaceName;
	//调用方法名   如 getSum
	private String methodName;
	//参数类型 按照接口参数顺序  getSum(int a, int b, String name)方法就是int.class int.class String.class的数组
	private Class[] paramsTypes;
	//参数 按照接口参数顺序 getSum(int a, int b, String name)方法就是 1,3,"Tom"的数组
	private Object[] parameters;

	public TransportMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransportMessage(String interfaceName, String methodName,
			Class[] paramsTypes, Object[] parameters) {
		super();
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.paramsTypes = paramsTypes;
		this.parameters = parameters;
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

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

}
