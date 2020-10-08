package com.simple.core.data.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 抽象出请求和返回公用协议
 * @author hejinguo
 * @version $Id: AbsDataMessage.java, v 0.1 2019年11月17日 下午5:01:59
 */
public abstract class AbsDataMessage {

    /**版本号*/
    protected Integer    version;

    /**协议分类*/
    protected Integer    category;

    /**设备类型(1.ios 2.android 3.wechat 4.web)*/
    protected Integer    platType;


    protected JSONObject data;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }



    public Integer getPlatType() {
        return platType;
    }

    public void setPlatType(Integer platType) {
        this.platType = platType;
    }



}
