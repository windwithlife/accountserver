package com.simple.account.controller;


import com.simple.common.api.BaseRequest;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.Authorize;
import com.simple.common.auth.LoginRequired;
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


@RestController
@RequestMapping("/pc/userService")
public class UsersController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UsersService usersService;

    @PostMapping(value = {"/signup"}, consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    BaseResponse signup(@RequestBody GenericRequest req,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        if (!this.usersService.register(req)) {
            throw new ServiceException("failed to register user");
        }
        BaseResponse res = this.login(req, request, response);
        return res;

    }


    @PostMapping(value = {"/login"}, consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    BaseResponse login(@RequestBody GenericRequest req,
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
            return res;
        }

    }

    @PostMapping(value = {"/validateUserLogin"}, consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    BaseResponse testVerify(@RequestHeader(AuthConstant.AUTHENTICATION_HEADER) String token,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            Sessions.validateToken(token);
            return BaseResponse.build().message(token);
        } catch (Exception ex) {
            return this.handleExeption(ex, "token is invalid");
        }
    }

    @LoginRequired
    @PostMapping(value = {"/testAuthentication"}, produces = {"application/json"})
    public @ResponseBody Object testLogin(@RequestBody GenericRequest req) {
        try {
            return BaseResponse.build().message(req.getString("username"));
        } catch (Exception ex) {
            return this.handleExeption(ex, "failed obtain jwk");
        }
    }

    @Authorize("guest")
    @PostMapping(value = {"/testAuthorize"}, produces = {"application/json"})
    public @ResponseBody Object testAuth(@RequestBody GenericRequest req) {
        try {
            return BaseResponse.build().message(req.getString("username"));
        } catch (Exception ex) {
            return this.handleExeption(ex, "failed obtain jwk");
        }
    }
    @GetMapping(value = {"/jwks"}, produces = {"application/json"})
    public @ResponseBody
    Object testJWKs(
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            return JwtUtils.createJWKs();
        } catch (Exception ex) {
            return this.handleExeption(ex, "failed obtain jwk");
        }
    }

}
