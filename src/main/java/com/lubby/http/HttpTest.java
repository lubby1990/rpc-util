package com.lubby.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author Lubby
 * @date 2015年4月23日 下午1:48:52
 *
 */

public class HttpTest {

	public static void main(String[] args) {

		String url = "http://www.baidu.com";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			
			String content = EntityUtils.toString(entity);
			System.out.println(content);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
