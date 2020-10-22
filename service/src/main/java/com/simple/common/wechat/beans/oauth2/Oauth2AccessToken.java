package com.simple.common.wechat.beans.oauth2;

/**
 * 页面授权oauth信息
 * @author hejinguo
 * @version $Id: Oauth2AccessToken.java, v 0.1 2020年8月17日 下午2:42:49
 */
public class Oauth2AccessToken {
    /**网页授权接口调用凭证*/
    private String  accessToken;
    /**接口调用凭证超时时间*/
    private Integer expiresIn;
    /**用户刷新access_token*/
    private String  refreshToken;
    /**用户唯一标识*/
    private String  openId;
    /**用户授权的作用域*/
    private String  scope;
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Integer getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
}
