package com.simple.account.controller;


import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.Sessions;
import com.simple.common.controller.BaseController;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;

import com.simple.common.error.ServiceException;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.exception.CommonExceptionHandle;
import com.simple.account.service.UsersService;
import com.simple.common.error.ServiceHelper;
import com.simple.common.token.JwtUtils;
import com.simple.common.token.ValidateLoginHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
            String token = this.usersService.login(username, password);
            Sessions.writeToken(token, "test.com", true, response);
            return BaseResponse.build();
        } catch (Exception e) {
            BaseResponse res = ServiceHelper.handleControllerException(e, "failed to login");
            return  res;
        }

    }


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
                                                           @RequestHeader(AuthConstant.AUTHENTICATION_HEADER) String token) {
        return  ValidateLoginHelp.validateToken(token);
    }



    @GetMapping(value = { "/verifyToken" }, produces = { "application/json" })
    public @ResponseBody BaseResponse testVerify(@RequestHeader("token") String token,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            JwtUtils.verifyToken(token);
            return BaseResponse.build().message(token);
        }catch (Exception ex) {
            return this.handleExeption(ex, "failed to register");
        }
    }

    @GetMapping(value = { "/jwks" }, produces = { "application/json" })
    public @ResponseBody Object testJWKs(
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        try {
            return JwtUtils.createJWKs();

        }catch (Exception ex) {
            return this.handleExeption(ex, "failed to register");
        }
    }

}
