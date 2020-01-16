package com.dohko.pay.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @desc Http请求工具类
 * @author luxiaohua
 */
public class HttpUtils {

    /**
     * 重试次数
     */
    private static final int RETRY_COUNT = 0;

    /**
     * POST 请求
     * @param url 请求url
     * @param params 请求参数
     * @param headers   请求头
     * @param connectTimeout 连接超时时间，毫秒
     * @param readTimeout 读超时时间，毫秒
     * @return
     */
    public static String post(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout, int readTimeout) {
        HttpPost httPost = new HttpPost(url);
        httPost.setConfig(getRequestConfig(connectTimeout, readTimeout));
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 设置参数
        if (params != null && params.size() > 0) {
            List<NameValuePair> paramList = getNameValuePairs(params);
            httPost.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
        }
        return post(httPost);
    }


    private static String post(HttpPost httpPost) {
        String result = null;

        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpClient = getCloseableHttpClient();
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(closeableHttpClient)) {
                    closeableHttpClient.close();
                }
                if (Objects.nonNull(closeableHttpResponse)) {
                    closeableHttpResponse.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }


    private static CloseableHttpClient getCloseableHttpClientWithSSL(SSLContext sslContext) {
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, false))
                .build();
        return closeableHttpClient;

    }

    private static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, false))
                .build();
        return closeableHttpClient;
    }

    /**
     * @param connectTimeout 连接超时时间
     * @param readTimeout   读超时时间
     * @return
     */
    private static RequestConfig getRequestConfig(int connectTimeout, int readTimeout) {
        return  RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .build();
    }

    private static List<NameValuePair> getNameValuePairs(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        return nameValuePairs;
    }

}
