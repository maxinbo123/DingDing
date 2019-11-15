package com.example.dingding.util;

import com.alibaba.fastjson.JSON;
import com.taobao.api.internal.toplink.embedded.websocket.util.StringUtil;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * okhttp工具类
 * 
 * @author renby
 * @version $Id: OkhttpUtil.java, v 0.1 2018年12月3日 上午11:44:25 renby Exp $
 */
public class OkhttpUtil {
    private static Logger interfaceLog = LoggerFactory.getLogger("interface-timeout");


    public static boolean isQuestionMark(String url) {
        if (url.indexOf("?") > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用于获取钉钉token
     * @param url
     * @param param
     * @return
     */
    public static  String requestByGetMethod(String url,Map<String,String> param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuilder entityStringBuilder = null;
        try {
            StringBuffer newUrl = new StringBuffer(url);
            for (Map.Entry<String, String> entry : param.entrySet()) {
                newUrl.append(isQuestionMark(newUrl.toString()) ? "&" : "?");
                newUrl.append(entry.getKey());
                newUrl.append("=");
                newUrl.append(entry.getValue());
            }
            HttpGet get = new HttpGet(newUrl.toString());
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(get);
            try {
                HttpEntity entity = httpResponse.getEntity();
                entityStringBuilder = new StringBuilder();
                if (null != entity) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8 * 1024);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        entityStringBuilder.append(line);
                    }
                }
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return entityStringBuilder.toString();
    }

}
