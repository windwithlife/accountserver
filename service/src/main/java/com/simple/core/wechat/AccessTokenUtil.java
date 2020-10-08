package com.simple.core.wechat;

import com.alibaba.fastjson.JSONObject;
import com.simple.common.props.ApplicationConfig;
import com.simple.core.httpClient.HttpClientHelp;
import com.simple.core.httpClient.util.HttpContentTypeEnum;
import com.simple.core.httpClient.util.HttpMethodEnum;
import com.simple.core.redis.JedisDBEnum;
import com.simple.core.redis.JedisHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信accessToken工具类
 * @author hejinguo
 * @version $Id: AccessTokenUtil.java, v 0.1 2019年11月21日 下午10:57:56
 */
public class AccessTokenUtil {
    private static final Logger logger                             = LoggerFactory
        .getLogger(AccessTokenUtil.class);
    /**云健康微信accessToken  redis   key*/
    private static final String REDIS_KEY_MEETINGLIVE_ACCESS_TOKEN = "MEETING-LIVE-WECHAT-ACCESS-TOKEN";

    /**
     * 获取微信AccessToken
     * @return
     */
    public static String getWechatAccessToken() {
        boolean existAccessToken = JedisHelper.getInstance()
            .exists(REDIS_KEY_MEETINGLIVE_ACCESS_TOKEN, JedisDBEnum.WECHAT);
        if (existAccessToken) {
            String accessToken = JedisHelper.getInstance().get(REDIS_KEY_MEETINGLIVE_ACCESS_TOKEN,
                JedisDBEnum.WECHAT);
            return accessToken;
        } else {
            //请求地址
            String requestUrl = ApplicationConfig.wecaht_meetinglive_meetinglive_accesstoken
                .replace("APPID", ApplicationConfig.wecaht_meetinglive_appid)
                .replace("APPSECRET", ApplicationConfig.wecaht_meetinglive_appsecret);
            //step2:请求并返回信息
            String result = HttpClientHelp.getInstance().submit(requestUrl, HttpMethodEnum.GET,
                HttpContentTypeEnum.FORM, null);
            if (StringUtils.isBlank(result)) {
                logger.error("获取微信AccessToken调用接口返回结果为空!");
                return null;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            int errcode = jsonObject.getIntValue("errcode");
            if (errcode != 0) {
                String errmsg = jsonObject.getString("errmsg");
                logger.error("获取微信AccessToken调用接口返回异常状态,结果信息：errcode--->{},errmsg--->{}", errcode,
                    errmsg);
                return null;
            }
            String accessToken = jsonObject.getString("access_token");
            int expiresIn = jsonObject.getInteger("expires_in");
            if (expiresIn > 100) {
                int allSeconds = expiresIn - 100;
                JedisHelper.getInstance().setex(REDIS_KEY_MEETINGLIVE_ACCESS_TOKEN, allSeconds,
                    accessToken, JedisDBEnum.WECHAT);
            }
            return accessToken;
        }
    }
}
