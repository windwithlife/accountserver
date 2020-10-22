package com.simple.account.controller;

import com.simple.account.service.ApiUsersService;
import com.simple.account.service.ApiWechatService;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.auth.Sessions;
import com.simple.common.controller.BaseController;
import com.simple.common.wechat.AdvancedUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping("api/wechatService")
public class ApiWechatController  extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiWechatController.class);
    @Autowired
    private ApiWechatService    apiWechatService;
    @Autowired
    private ApiUsersService     apiUsersService;

    /**
     * 根据code获取微信openId
     * @return
     */
    @PostMapping(value = { "/getWechatOpenId" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody BaseResponse getWechatOpenId(@RequestBody GenericRequest req) {
        //返回对象
        GenericResponse res = GenericResponse.build();
        //step1:参数验证
        String code = req.getString("code");
        if (StringUtils.isBlank(code)) {
            return res.message("微信code不能为空!");
        }
        try {
            //step2:获取openId
            String openId = this.apiWechatService.getWechatOpenId(code);
            //step3:返回结果
            res.addKey$Value("openId", openId);
            return res.message("获取微信openId成功!");

        } catch (Exception e) {
            return this.handleExeption(e, "failed to get wechat OpenId");
        }

    }

    /**
     * 手机号授权登录或注册
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/registerUser" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody BaseResponse registerUser(@RequestBody GenericRequest req,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        try {
            //step1:用户注册或登录
            String domainName  = req.getString("domain");
            Map<String, Object> resultMap = this.apiUsersService.registerUser(req);
            //step3:返回结果
            Sessions.writeToken((String)resultMap.get("token"),domainName,true,response);
            return GenericResponse.build().addKey$Value("openId", resultMap.get("openId"))
                   .addKey$Value("token", resultMap.get("token"))
                   .addKey$Value("isNewUser", resultMap.get("isNewUser"))
                   .addKey$Value("newUserMessage", resultMap.get("newUserMessage"));

        } catch (Exception e) {
            return this.handleExeption(e,"failed to register wechat user");
        }
    }
    
    /**
     * 获取微信用户授权url
     * @param request
     * @return
     */
    @PostMapping(value = { "/getWechatPublicOauthUrl" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody
    BaseResponse getWechatOauthUrl(@RequestBody GenericRequest req,
                                   HttpServletRequest request) {

        //step  1:获取请求参数
        String href = req.getString("href");
        if (StringUtils.isBlank(href)) {
            return BaseResponse.build().message("请求参数href不能为空!");
        }
        //step  2:获取微信授权请求url地址
        String url = AdvancedUtil.getOauth2AccessTokenUrl("snsapi_userinfo", href);
        return GenericResponse.build().addKey$Value("oauthUrl", url);
    }
    
    /**
     * 微信公众号授权登录或注册
     * @param response
     * @return
     */
    @PostMapping(value = { "/registerWechatPublicUser" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody BaseResponse registerWechatPublicUser(@RequestBody GenericRequest req,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) {
        try {
            //step1:用户注册或登录
            String domainName  = req.getString("domain");
            Map<String, Object> resultMap = this.apiUsersService.registerWechatPublicUser(req);
            //step3:返回结果
            String token = (String)resultMap.get("token");
            Sessions.writeToken(token,domainName,true,response);
            return GenericResponse.build().addKey$Value("openId", resultMap.get("openId"))
                    .addKey$Value("token", resultMap.get("token"));

        } catch (Exception e) {
            return this.handleExeption(e,"failed to register wechat public user");
        }
    }
}
