package com.simple.core.data.request;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.core.data.message.RequestMessage;

/**
 * json格式请求
 * @author hejinguo
 * @version $Id: JsonMessage.java, v 0.1 2019年11月17日 下午5:02:59
 */
public class JsonMessage extends RequestMessage {
    private String sourceMessage;

    public JsonMessage() {
        this.data = new JSONObject();
    }

    public JsonMessage(JsonMessage jsonMessage) {
        this.data = new JSONObject();
        setVersion(jsonMessage.getVersion());
        setCategory(jsonMessage.getCategory());
        setPlatType(jsonMessage.getPlatType());
//        setPlatForm(jsonMessage.getPlatForm());
//        setToken(jsonMessage.getToken());
    }

    public JsonMessage(Integer version, Integer category, Integer platType, String platform,
                       String sourceMessage, Integer state, JSONObject data) {
        this.version = version;
        this.category = category;
        this.platType = platType;
        //this.platForm = platform;
        this.sourceMessage = sourceMessage;
        this.data = data;
    }

    public JsonMessage(Integer version, Integer category, Integer platType, String platform,
                       String sourceMessage, Integer option, Integer state, JSONObject data) {
        this.version = version;
        this.category = category;
        this.platType = platType;
        //this.platForm = platform;
        this.sourceMessage = sourceMessage;
        this.data = data;
    }

    public Object get(Object key) {
        // TODO Auto-generated method stub
        return this.data.get(key);
    }

    public JsonMessage getJsonMessage(String key) {
        JsonMessage message = new JsonMessage();
        message.data = this.data.getJSONObject(key);
        message.sourceMessage = this.data.getJSONObject(key).toJSONString();
        return message;
    }

    public JsonMessage[] getJsonMessageArray(String key) {
        JSONArray jsonArray = this.data.getJSONArray(key);
        JsonMessage[] jsonMessageArray = new JsonMessage[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            JsonMessage jsonMessage = new JsonMessage();
            jsonMessage.data = jsonObj;
            jsonMessage.sourceMessage = jsonObj.toJSONString();
            jsonMessageArray[i] = jsonMessage;
        }
        return jsonMessageArray;
    }

    public <T> T get(String key, Class<T> clazz) {
        // TODO Auto-generated method stub
        return JSON.toJavaObject(this.data.getJSONObject(key), clazz);
    }

    public <T> List<T> getTList(String key, Class<T> clazz) {
        return JSON.parseArray(this.data.getString(key), clazz);
    }

    public boolean getBoolean(String key) {
        // TODO Auto-generated method stub
        return this.data.getBooleanValue(key);
    }

    public boolean[] getBooleans(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        boolean[] booleans = new boolean[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            booleans[i] = jsonArray.getBooleanValue(i);
        }
        return booleans;
    }

    public byte getByte(String key) {
        // TODO Auto-generated method stub
        return this.data.getByteValue(key);
    }

    public byte[] getBytes(String key) {
        // TODO Auto-generated method stub
        return this.data.getBytes(key);
    }

    public String getjsonMessage() {
        // TODO Auto-generated method stub
        return this.data.toJSONString();
    }

    public double getDouble(String key) {
        // TODO Auto-generated method stub
        return this.data.getDoubleValue(key);
    }

    public double[] getDoubles(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        double[] doubles = new double[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            doubles[i] = jsonArray.getDoubleValue(i);
        }
        return doubles;
    }

    public float getFloat(String key) {
        // TODO Auto-generated method stub
        return this.data.getFloatValue(key);
    }

    public float[] getFloats(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        float[] floats = new float[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            floats[i] = jsonArray.getFloatValue(i);
        }
        return floats;
    }

    public int getInt(String key) {
        // TODO Auto-generated method stub
        return this.data.getIntValue(key);
    }

    public Integer getInteger(String key) {
        return this.data.getInteger(key);
    }

    public int[] getInts(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        int[] ints = new int[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            ints[i] = jsonArray.getIntValue(i);
        }
        return ints;
    }

    public long getLong(String key) {
        // TODO Auto-generated method stub
        return this.data.getLongValue(key);
    }

    public long[] getLongs(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        long[] longs = new long[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            longs[i] = jsonArray.getLongValue(i);
        }
        return longs;
    }

    public String getString(String key) {
        // TODO Auto-generated method stub
        return this.data.getString(key);
    }

    public String[] getStrings(String key) {
        // TODO Auto-generated method stub
        JSONArray jsonArray = this.data.getJSONArray(key);
        String[] strings = new String[jsonArray.size()];
        for (int i = 0, n = jsonArray.size(); i < n; i++) {
            strings[i] = jsonArray.getString(i);
        }
        return strings;
    }

    public String getSourceMessage() {
        return sourceMessage;
    }

    public void setSourceMessage(String sourceMessage) {
        this.sourceMessage = sourceMessage;
    }

    public boolean containsKey(Object key) {
        return this.data.containsKey(key);
    }

    @Override
    public String toString() {
        return "JsonMessage [jsonMessage=" + data + ", version=" + version + ", category="
               + category + ",platType=" + platType + ",  source="
               + sourceMessage + "]";
    }

}
