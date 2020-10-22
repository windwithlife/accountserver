package com.simple.account.service;

import com.alibaba.fastjson.JSONObject;
import com.simple.common.props.ApplicationConfig;
import com.simple.common.error.ServiceException;
import com.simple.common.httpClient.HttpClientHelp;
import com.simple.common.httpClient.util.HttpContentTypeEnum;
import com.simple.common.httpClient.util.HttpMethodEnum;
import com.simple.common.redis.JedisDBEnum;
import com.simple.common.redis.JedisHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 微信相关业务层
 * @author hejinguo
 * @version $Id: WechatService.java, v 0.1 2020年7月18日 下午2:40:13
 */
@Service
public class ApiWechatService {
    private static final Logger     logger = LoggerFactory.getLogger(ApiWechatService.class);

    /**
     * 微信根据code获取openId
     * @param code
     * @return
     * @throws Exception
     */
    public String getWechatOpenId(String code) throws Exception {
        //step1:获取并替换url地址
        String requestUrl = ApplicationConfig.wecaht_meetinglive_jscode2session
            .replace("APPID", ApplicationConfig.wecaht_meetinglive_appid)
            .replace("SECRET", ApplicationConfig.wecaht_meetinglive_appsecret)
            .replace("JSCODE", code);
        //step2:请求并返回信息
        String result = HttpClientHelp.getInstance().submit(requestUrl, HttpMethodEnum.GET,
            HttpContentTypeEnum.FORM, null);
        if (StringUtils.isEmpty(result)) {
            logger.error("获取微信openId返回结果为空!参数信息:code-->{},result-->{}", code, result);
            throw new ServiceException("获取微信信息失败,请重新打开!");
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        int errcode = jsonObject.getIntValue("errcode");
        if (errcode != 0) {
            logger.error("获取微信openId返回状态失败!参数信息:code-->{},errcode--->{},result-->{}", code,
                errcode, result);
            throw new ServiceException("获取微信信息失败,请重新打开!");
        }
        //step3:获取openId信息
        String openId = jsonObject.getString("openid");
        if (StringUtils.isBlank(openId)) {
            logger.error("获取微信openId返回openId为空!参数信息:code-->{},openId--->{},result-->{}", code,
                openId, result);
            throw new ServiceException("获取微信信息失败,请重新打开!");
        }
        String sessionKey = jsonObject.getString("session_key");
        //step4:存储在redis中
        JedisHelper.getInstance().set(openId, sessionKey, JedisDBEnum.WECHAT);
        return openId;
    }
}
