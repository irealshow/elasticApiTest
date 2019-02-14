package com.elastic.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EsJavaApi {
//	private static final Logger LOGGER = LoggerFactory.getLogger(EsJavaApi.class);

    private static TransportClient client = TransportClientUtil.getTransportClient();

    public static void main(String[] args) {
        //创建索引文档
//        save();

        //搜索文档
        get();

        //更新文档
        //update();

        //删除文档
        //delete();

        //关闭链接
        client.close();

    }

    /**
     * pojo bean ,创建index 文档
     */
    private static void save(){
        Book book = new Book();
        book.setName("jvm 虚拟机5");
        book.setAuthor("lcj4");
        book.setPublishTime(new Date().toString());
        book.setPrice(512.10);
        book.setId("17");

        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] bytes = mapper.writeValueAsBytes(book);
            IndexResponse response = client.prepareIndex("books","book",book.getId())
                    .setSource(bytes)
                    .get();

            System.out.println(response.getResult()); //CREATED
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id,搜索文档
     */
    private static void get(){
        GetResponse response = client.prepareGet("books","book","17")
                .setOperationThreaded(false) //默认为true,在不同的线程执行
                .get();

        //{"id":"17","name":"jvm 虚拟机5","author":"lcj4","price":512.1,"publishTime":"Thu Jun 22 13:36:49 CST 2017"}
        System.out.println(response.getSourceAsString());
    }


    /**
     * 更新文档
     */
    private static void update(){
        UpdateRequest updateRequest = new UpdateRequest();
        //指定索引
        updateRequest.index("books");
        //指定类型
        updateRequest.type("book");
        //指定id
        updateRequest.id("17");

        Book book = new Book();
        book.setName("jvm 虚拟机55");
        book.setAuthor("lcj4");
//        book.setPublishTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        book.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        book.setPrice(512.18);
        book.setId("17");

        ObjectMapper mapper = new ObjectMapper();

        //有很多重载方法
        //{"_index":"book","_type":"book","_id":"17","_version":2,"found":true,"_source":{"id":"17","name":"jvm 虚拟机55","author":"lcj4","price":512.18,"publishTime":"2017-06-22 13:45:22"}}
        try {
            updateRequest.doc(mapper.writeValueAsBytes(book));
            client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*根据id删除文档*/
    private static void delete(){
        DeleteResponse response = client.prepareDelete("books","book","17")
                .get();
        //DeleteResponse[index=book,type=book,id=17,version=3,result=deleted,shards=ShardInfo{total=2, successful=1, failures=[]}]
        System.out.println(response);
    }
}
