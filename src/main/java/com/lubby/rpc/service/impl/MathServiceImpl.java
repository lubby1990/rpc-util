package com.lubby.rpc.service.impl;

import com.lubby.rpc.service.MathService;

/**
 * @author Lubby
 * @date 2015年4月24日 下午2:32:56
 *
 */

public class MathServiceImpl implements MathService {
	public int getSum(int a, int b, String name) {
		System.out.println(name);
		return a + b;
	}

}
