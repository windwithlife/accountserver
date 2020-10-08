package com.simple.account.controller;


import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.Sessions;
import com.simple.common.controller.BaseController;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.auth.AuthContext;
import com.simple.common.error.ServiceException;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.exception.CommonExceptionHandle;
import com.simple.account.service.UsersService;
import com.simple.common.error.ServiceHelper;
import com.simple.core.token.ValidateLoginHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理控制层
 * @author hejinguo
 * @version $Id: UsersController.java, v 0.1 2020年7月13日 上午11:10:13
 */
@RestController
@RequestMapping("/pc/userService")
public class UsersController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UsersService     usersService;



    /**
     * 测试普通用户注册
     */
    @PostMapping(value = { "/signup" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody BaseResponse signup(@RequestBody GenericRequest req,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        try {
            if (!this.usersService.register(req)) {
                throw new ServiceException("failed to register user");
            }
            BaseResponse res = this.login(req,request, response);
            return  res;
        }catch (Exception ex) {
            return this.handleExeption(ex, "failed to register");
        }
    }

    /**
     * pc用户登录
     */
    @PostMapping(value = { "/login" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody BaseResponse login(@RequestBody  GenericRequest req,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        String username = req.getString("username");
        String password = req.getString("password");
        try {
            //step1:pc用户登录
            Map<String, Object> resultMap = this.usersService.login(username, password);
            String token = (String)resultMap.get("token");
            Sessions.writeToken(token, "test.com", true, response);
            return BaseResponse.build();
        } catch (Exception e) {
            BaseResponse res = ServiceHelper.handleControllerException(e, "failed to login");
            return  res;
        }

    }

    /**
     * pc用户登录
     */
    @PostMapping(value = { "/userLogin" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage userLogin(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        AuthContext.getAuthz();
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:pc用户登录
            Map<String, Object> resultMap = this.usersService.userLogin(jsonMessage);
            String token = (String)resultMap.get("token");
            //step3:返回结果
            resMessage.addKey$Value("token", token);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
            this.writeCookie(true,token,"test.com",response);
            this.writeHeaderToken(token,response);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取主页菜单列表
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
//    @PostMapping(value = { "/getUserMenu" }, consumes = { "application/json" }, produces = { "application/json" })
//    //@LoginRequired
//    public @ResponseBody ResponseMessage getUserMenu(@RequestBody JsonMessage jsonMessage,
//                                                   HttpServletRequest request,
//                                                   HttpServletResponse response) {
//        //返回对象
//        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
//        try {
//            //step1:获取主页菜单
//            Map<String, Object> resultMap = this.usersService.getUserMenu(jsonMessage);
//            //step3:返回结果
//            resMessage.addKey$Value("id", resultMap.get("id"));
//            resMessage.addKey$Value("userTrueName", resultMap.get("userTrueName"));
//            resMessage.addKey$Value("headPic", resultMap.get("headPic"));
//            resMessage.addKey$Value("menuList", resultMap.get("menuList"));
//            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
//            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
//        } catch (Exception e) {
//            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
//        }
//        return resMessage;
//    }

    /**
     * 获取讲师列表
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getUserInfoList" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage getUserInfoList(@RequestBody JsonMessage jsonMessage,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {

            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取讲师详情
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getUserInfoDetail" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage getUserInfoDetail(@RequestBody JsonMessage jsonMessage,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {

            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 添加讲师
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/addUserInfo" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage addUserInfo(@RequestBody JsonMessage jsonMessage,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {

            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 获取讲师列表
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getInstructorList" }, consumes = { "application/json" }, produces = { "application/json" })
   // @LoginRequired
    public @ResponseBody ResponseMessage getInstructorList(@RequestBody JsonMessage jsonMessage,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {

            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }


    @PostMapping(path = "/test")
    BaseResponse changeEmail(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    /**
     * 验证当前用户是否登录
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/validateUserLogin" }, consumes = { "application/json" }, produces = { "application/json" })
    public BaseResponse validateUserLogin(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           @RequestHeader(AuthConstant.AUTHENTICATION_HEADER) String auth) {

        return  ValidateLoginHelp.validateToken(auth);
    }



    private  void writeCookie(boolean rememberMe, String token, String domainName, HttpServletResponse response) {
        long SHORT_SESSION = TimeUnit.HOURS.toMillis(12L);
        long LONG_SESSION = TimeUnit.HOURS.toMillis(720L);
        long duration;
        if (rememberMe) {
            duration = LONG_SESSION;
        } else {
            duration = SHORT_SESSION;
        }

        int maxAge = (int)(duration / 1000L);
        //String token = Sign.generateSessionToken(userId, signingSecret, support, duration);
        //String token = JwtUtils.createToken2(userId);
        Cookie cookie = new Cookie("Authentication", token);
        cookie.setPath("/");
        cookie.setDomain(domainName);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private  void writeHeaderToken(String token, HttpServletResponse response) {
        response.addHeader("token", token);
    }

}
