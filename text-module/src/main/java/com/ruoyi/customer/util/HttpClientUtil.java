package com.ruoyi.customer.util;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author: xianpei.qin
 * @create: 2020-04-14 19:26
 **/
public class HttpClientUtil {


    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    public static final int connTimeout = 10000;
    public static final int readTimeout = 10000;
    public static final String charSet = "utf-8";


    public static String doPost(String url, String params, String contentType) throws IOException {
        return doPost( url, params,  contentType,connTimeout, readTimeout);
    }
    public static String doPost(String url,String param,String contentType,Integer connTimeout,Integer readTimeout) throws IOException {
        log.info("请求地址：{}" ,url);
        log.info("请求参数：{}" ,param);
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", contentType);
        //设置请求超时参数
        RequestConfig.Builder customReqConf = RequestConfig.custom();
        if (connTimeout != null) {
            customReqConf.setConnectTimeout(connTimeout);
        }
        if (readTimeout != null) {
            customReqConf.setSocketTimeout(readTimeout);
        }
        httpPost.setConfig(customReqConf.build());
        //设置请求参数到请求体
        httpPost.setEntity(new StringEntity(param, charSet));
        // 得到返回的response
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity,charSet);
        }
        EntityUtils.consume(entity);
        response.close();
        log.info("响应结果：{}", result);
        return result;
    }

    public static String doGet(String url, String params, String contentType) throws IOException {
        return doPost( url, params,  contentType,connTimeout, readTimeout);
    }

    public static String doGet(String url,String param,String contentType,Integer connTimeout,Integer readTimeout) throws IOException {
        log.info("请求地址：{}" ,url);
        log.info("请求参数：{}" ,param);
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(url);

        // 设置超时参数
        RequestConfig.Builder customReqConf = RequestConfig.custom();
        if (connTimeout != null) {
            customReqConf.setConnectTimeout(connTimeout);
        }
        if (readTimeout != null) {
            customReqConf.setSocketTimeout(readTimeout);
        }
        httpGetWithEntity.setConfig(customReqConf.build());
        //设置请求参数到请求体
        HttpEntity httpEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
        httpGetWithEntity.setEntity(httpEntity);
        CloseableHttpResponse response = httpClient.execute(httpGetWithEntity);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            result = EntityUtils.toString(entity,"utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        log.info("响应结果：{}", result);
        return result;

    }
}