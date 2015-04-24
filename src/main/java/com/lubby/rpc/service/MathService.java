package com.lubby.rpc.service;
/**
 *     @author Lubby
 *     @date 2015年4月22日  下午1:41:58
 *
 */

public class MathService {
	
	public  int getSum(int a,int b,String name){
		System.out.println(name);
		return a + b;
	}
	

}
