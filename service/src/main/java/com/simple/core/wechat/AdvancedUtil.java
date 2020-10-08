package com.simple.core.wechat;

import com.alibaba.fastjson.JSONObject;
import com.simple.common.props.ApplicationConfig;
import com.simple.core.httpClient.HttpClientHelp;
import com.simple.core.httpClient.util.HttpContentTypeEnum;
import com.simple.core.httpClient.util.HttpMethodEnum;
import com.simple.core.redis.JedisDBEnum;
import com.simple.core.redis.JedisHelper;
import com.simple.core.wechat.beans.oauth2.Oauth2AccessToken;
import com.simple.core.wechat.beans.oauth2.WechatUserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 获取网页授权信息
 * @author hejinguo
 * @version $Id: AdvancedUtil.java, v 0.1 2020年8月21日 下午9:50:45
 */
public class AdvancedUtil {
    private static final Logger logger                                    = LoggerFactory
        .getLogger(AdvancedUtil.class);
    /**健云公众号accessToken  redis   key*/
    private static final String REDIS_KEY_MEETINGLIVE_PUBLIC_ACCESS_TOKEN = "MEETING-LIVE-WECHATPUBLIC-ACCESS-TOKEN";

    /**
     * 获取页面授权请求url地址
     * @param scode
     * @param callbackUrl 回调url
     * @return
     */
    public static String getOauth2AccessTokenUrl(String scode, String callbackUrl) {
        String requestUrl = ApplicationConfig.userAauth2
            .replace("APPID", ApplicationConfig.publicAppId).replace("REDIRECT_URI", callbackUrl)
            .replace("SCOPE", scode);
        return requestUrl;
    }

    /**
     * 获取微信公众号AccessToken
     * @return
     */
    public static Oauth2AccessToken getOauth2AccessToken(String code) {
        //step1:调用接口获取accessToken
        String requestUrl = ApplicationConfig.oauthAccessToken
            .replace("APPID", ApplicationConfig.publicAppId)
            .replace("APPSECRET", ApplicationConfig.publicSecret).replace("CODE", code);
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
        String openId = jsonObject.getString("openid");
        if (StringUtils.isBlank(openId)) {
            logger.error("获取公众号授权信息返回openId为空!参数信息:code-->{},openId--->{},result-->{}", code,
                openId, result);
            return null;
        }
        String accessToken = jsonObject.getString("access_token");
        int expiresIn = jsonObject.getInteger("expires_in");
        String refreshToken = jsonObject.getString("refresh_token");
        String scope = jsonObject.getString("scope");
        //step3:封装返回信息
        Oauth2AccessToken outhAccessToken = new Oauth2AccessToken();
        outhAccessToken.setAccessToken(accessToken);
        outhAccessToken.setExpiresIn(expiresIn);
        outhAccessToken.setOpenId(openId);
        outhAccessToken.setRefreshToken(refreshToken);
        outhAccessToken.setScope(scope);
        //step4:设置缓存
        if (expiresIn > 100) {
            int allSeconds = expiresIn - 100;
            JedisHelper.getInstance().setex(REDIS_KEY_MEETINGLIVE_PUBLIC_ACCESS_TOKEN, allSeconds,
                accessToken, JedisDBEnum.WECHAT);
        }
        return outhAccessToken;
    }
    
    /**
     * 缓存中获取accessToken
     * @return
     */
    public  static  String  getAccessTokenByCache() {
        //step1:获取缓存中accessToken
        boolean existAccessToken = JedisHelper.getInstance()
            .exists(REDIS_KEY_MEETINGLIVE_PUBLIC_ACCESS_TOKEN, JedisDBEnum.WECHAT);
        if (existAccessToken) {
            String accessToken = JedisHelper.getInstance()
                .get(REDIS_KEY_MEETINGLIVE_PUBLIC_ACCESS_TOKEN, JedisDBEnum.WECHAT);
            return accessToken;
        }
       return  null;
    }
    
    /**
     * 公众号获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public  static  WechatUserInfo  getOauthUserInfo(String accessToken,String openId) {
        //step1:获取并替换url地址
          String requestUrl = ApplicationConfig.oauthUserInfo.replace("ACCESS_TOKEN", accessToken)
              .replace("OPENID", openId);
          //step2:请求并返回信息
          String result = HttpClientHelp.getInstance().submit(requestUrl, HttpMethodEnum.GET,
              HttpContentTypeEnum.FORM, null);
          if (StringUtils.isEmpty(result)) {
              logger.error("获取公众号授权用户信息返回结果为空!参数信息:accessToken-->{},openId-->{},result-->{}",accessToken, openId, result);
              return null;
          }
          try {
            result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("获取公众号授权用户信息转码异常!", e);
            return null;
        }
          JSONObject jsonObject = JSONObject.parseObject(result);
          int errcode = jsonObject.getIntValue("errcode");
          if (errcode != 0) {
              logger.error("获取公众号授权用户信息返回状态失败!参数信息:accessToken-->{},openId-->{},result-->{}",accessToken, openId, result);
              return null;
          }
          //step4:返回数据
          String openid = jsonObject.getString("openid");
          String nickname = jsonObject.getString("nickname");
          Integer sex = jsonObject.getInteger("sex");
          String province = jsonObject.getString("province");
          String city = jsonObject.getString("city");
          String country = jsonObject.getString("country");
          String headimgurl = jsonObject.getString("headimgurl");
          String unionid = jsonObject.getString("unionid");
          WechatUserInfo userInfo = new WechatUserInfo();
          userInfo.setOpenId(openid);
          userInfo.setNickname(nickname);
          userInfo.setSex(sex);
          userInfo.setProvince(province);
          userInfo.setCity(city);
          userInfo.setCountry(country);
          userInfo.setHeadimgurl(headimgurl);
          userInfo.setUnionid(unionid);
          return userInfo;
      }

}
