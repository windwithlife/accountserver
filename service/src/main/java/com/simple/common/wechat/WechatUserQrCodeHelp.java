/**
 * DianMei.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package com.simple.common.wechat;

import com.simple.common.utils.JsonUtil;
import com.simple.common.props.ApplicationConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取微信分享二维码
 * @author hejinguo
 * @version $Id: WechatUserQrCodeHelp.java, v 0.1 2019年12月16日 下午6:38:51
 */
public class WechatUserQrCodeHelp {
   /**
    * 生成用户微信二维码
    * @param file
    * @param scene
    * @param page
    * @param width
    * @return
    * @throws Exception
    */
    public  static  byte[] getWechatUserQrCode(File file,String scene,String page,String width)throws  Exception{
        byte[] result = null;
        //step1:封装请求参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("scene", scene);
        paramMap.put("page", page);
        paramMap.put("width", width);
        //step2:获取accessToken
        String accessToken = AccessTokenUtil.getWechatAccessToken();
        //step3:请求接口发送消息
        String requestUrl = ApplicationConfig.wecaht_meetinglive_getwxacodeunlimit
            .replace("ACCESS_TOKEN", accessToken);
        String body =JsonUtil.writeObjectJSON(paramMap);
        StringEntity entity = new StringEntity(body);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        
        CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(requestUrl);  // 接口
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //获取返回结果
            HttpEntity responseEntity = response.getEntity();
            result=EntityUtils.toByteArray(responseEntity);
        }
        return  result;
    }
}
