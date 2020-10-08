package com.simple.core.httpClient;

import com.simple.core.httpClient.util.HttpContentTypeEnum;

/**
 * HttpClient接口定义
 * @author hejinguo
 * @version $Id: IHttpClientService.java, v 0.1 2019年11月17日 下午5:13:02
 */
public interface IHttpClientService {
    /**
     * 采用post提交方式
     * @param url 需要调用的接口地址
     * @param parameterMap 需要提交的参数数据,此处可以传入HashMap以及实体对象,
     * map中key值需要对应service服务端中对应方法的参数名,实体对象中key值为对应的实体类名称,
     * 需注意名称首字母小写(需要对应service服务端中对应方法的参数名),value值为json字符串
     * @return  返回统一为json格式字符串
     */
    String post(String url, HttpContentTypeEnum contentType, Object parameterMap);

    /**
     * 采用get提交方式
     * @param url 需要调用的接口地址
     * @param parameterMap 需要提交的参数数据,此处可以传入HashMap以及实体对象,
     * map中key值需要对应service服务端中对应方法的参数名,实体对象中key值为对应的实体类名称,
     * 需注意名称首字母小写(需要对应service服务端中对应方法的参数名),value值为json字符串
     * @return  返回统一为json格式字符串
     */
    String get(String url, Object parameterMap);
}
