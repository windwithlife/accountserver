package com.simple.core.wechat.beans.oauth2;

import java.io.Serializable;

/**
 * 微信授权用户信息实体
 * @author hejinguo
 * @version $Id: WechatUserInfo.java, v 0.1 2020年8月20日 下午10:19:52
 */
public class WechatUserInfo implements Serializable {
    /**  */
    private static final long serialVersionUID = 8409110424330704492L;

    private String            openId;

    private String            nickname;

    private Integer           sex;

    private String            province;

    private String            city;

    private String            country;

    private String            headimgurl;

    private String            unionid;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "WechatUserInfo [openId=" + openId + ", nickname=" + nickname + ", sex=" + sex
               + ", province=" + province + ", city=" + city + ", country=" + country
               + ", headimgurl=" + headimgurl + ", unionid=" + unionid + "]";
    }
}
