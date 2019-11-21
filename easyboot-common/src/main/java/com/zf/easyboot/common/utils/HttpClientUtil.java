package com.zf.easyboot.common.utils;


import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @description:http工具类
 * @fileName:HttpClientUtils.java
 * @author zhangwei
 */

public class HttpClientUtil {
    private static Logger logger =  LogManager.getLogger(HttpClientUtil.class);

    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = createHttpClient();
        String resultString = "";
        CloseableHttpResponse response = null;
        HttpGet httpGet = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            logger.info("http远程调用失败！");
        } finally {
            free(httpGet,response,httpclient);
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);// 创建Http Post请求
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.info("http远程调用失败！");
        } finally {
            free(httpPost,response,httpClient);
        }
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 请求的参数类型为json
     * @param url
     * @param json
     * @return
     * {username:"",pass:""}
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);// 创建Http Post请求
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.info("http远程调用失败！");
        } finally {
            free(httpPost,response,httpClient);
        }
        return resultString;
    }


    /**
     * 创建httpclient
     * @return
     */
    private static CloseableHttpClient createHttpClient(){
        return HttpClients.createDefault();
    }

    /**
     * 创建RequestConfig
     * @return
     */
    private static RequestConfig createRequestConfig(){
        return RequestConfig.custom().setSocketTimeout(1000*60).setConnectTimeout(1000*60)
                .build();
    }

    /**
     * 释放资源
     * @param httpRequestBase
     * @param closeableHttpResponse
     * @param closeableHttpClient
     */
    private static void free(HttpRequestBase httpRequestBase,
                             CloseableHttpResponse closeableHttpResponse,CloseableHttpClient closeableHttpClient){
        try {
            if(httpRequestBase != null){
                httpRequestBase.releaseConnection();
            }
            if(closeableHttpResponse != null){
                closeableHttpResponse.close();
            }
            if(closeableHttpClient != null){
                closeableHttpClient.close();
            }
        } catch (IOException e) {
            logger.error("http资源释放失败");
        }
    }

}
