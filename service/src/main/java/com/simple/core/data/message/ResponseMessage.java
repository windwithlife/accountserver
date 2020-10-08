package com.simple.core.data.message;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.simple.core.data.request.JsonMessage;

/**
 * 请求响应协议头
 * @author hejinguo
 * @version $Id: ResponseMessage.java, v 0.1 2019年11月17日 下午5:02:38
 */
public class ResponseMessage extends AbsDataMessage {
    /**失败*/
    public static final int    FAILURE_CODE           = 0;
    /**成功*/
    public static final int    SUCCESS_CODE           = 1;
    /**未登录*/
    public static final int    NO_LOGIN               = 2;

    public static final String SUCCESS_MESSAGE        = "操作成功";

    public static final String ADD_SUCCESS_MESSAGE    = "添加成功";

    public static final String UPDATE_SUCCESS_MESSAGE = "修改成功";

    public static final String DELETE_SUCCESS_MESSAGE = "删除成功";

    public static final String SELECT_SUCCESS_MESSAGE = "查询成功";

    /**用户未登录*/
    public static final String NO_LOGIN_MESSAGE       = "非法请求,请确认是否已登录";

    /**请求成功状态(0:成功  1：失败)*/
    protected Integer          status;

    /**响应描述*/
    protected String           message;

    public ResponseMessage() {

    }

    public ResponseMessage(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public ResponseMessage(JsonMessage jsonMessage) {
        if (jsonMessage != null) {
            this.data = new JSONObject();
            this.setVersion(jsonMessage.getVersion());
            this.setCategory(jsonMessage.getCategory());
            this.setPlatType(jsonMessage.getPlatType());
//            this.setPlatForm(jsonMessage.getPlatForm());
//            this.setToken(jsonMessage.getToken());
//            this.setOpenId(jsonMessage.getOpenId());
            this.setStatus(ResponseMessage.FAILURE_CODE);
        }
    }

    public void addBeanList(String key, List<?> list) {
        data.put(key, list);
    }

    public void addKey$Value(String key, Object value) {
        data.put(key, value);
    }

    public void addBean(String key, Object bean) {
        data.put(key, bean);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
