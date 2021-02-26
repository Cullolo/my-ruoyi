package com.ruoyi.customer.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/02/24 10:56
 */
public class ESClientUtil {
    public static RestHighLevelClient getClient() {
        //创建HttpHost对象
        HttpHost httpHost = new HttpHost("192.168.78.100", 9200);
        //创建RestClientBuilder
        RestClientBuilder builder = RestClient.builder(httpHost);
        //创建RestHighLevelClien对象
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }}