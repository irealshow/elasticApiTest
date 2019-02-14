package com.elastic.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TransportClientUtil {
	public static TransportClient getTransportClient(){
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch") //设置集群的名字,默认是elasticsearch
//                .put("client.transport.sniff",true) //启用监听器,每5秒刷新一次nodes
                .build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
            //设置node
            //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(System.getenv("COMPUTERNAME")),9300));
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("47.99.78.120"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
