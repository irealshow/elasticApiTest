package com.elastic.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Hello world!
 *
 */
public class App
{ 
    @SuppressWarnings("resource")
	public static void main( String[] args ) throws UnknownHostException
    {
    	Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("47.99.78.120"), 9300));
		//***.***.***.*** 表示 ip 地址，本地的话，可以使用 localhost，9300是默认的 api 访问接口
		GetResponse getResponse = client.prepareGet("accounts", "person", "1").execute().actionGet();
        System.out.println(getResponse.getSourceAsString());
    }
}
