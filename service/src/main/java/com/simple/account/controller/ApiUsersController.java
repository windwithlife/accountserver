package com.simple.account.controller;

import com.simple.account.service.ApiUsersService;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;
import com.simple.core.exception.CommonExceptionHandle;
//import com.simple.core.spring.interceptor.annotation.LoginRequired;
import com.simple.core.token.ValidateLoginHelp;
import com.simple.account.model.RegionModel;
import com.simple.account.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理控制层
 * @author hejinguo
 * @version $Id: UsersController.java, v 0.1 2020年7月13日 上午11:10:13
 */
@RestController
@RequestMapping("api/userService")
public class ApiUsersController {
    private static final Logger logger = LoggerFactory.getLogger(ApiUsersController.class);
    @Autowired
    private ApiUsersService     apiUsersService;

    /**
     * 验证当前用户是否登录
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/validateUserLogin" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage validateUserLogin(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody JsonMessage jsonMessage) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            ResponseMessage responseMessage = ValidateLoginHelp.validateUserLogin(jsonMessage);
            if (responseMessage.getStatus() == ResponseMessage.SUCCESS_CODE) {
                resMessage.addKey$Value("isLogin", 1);
                resMessage.setMessage(responseMessage.getMessage());
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            } else {
                resMessage.addKey$Value("isLogin", 0);
                resMessage.setMessage(responseMessage.getMessage());
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            }
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 用户退出登录
     * @param request
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/loginOut" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage loginOut(HttpServletRequest request,
                                                  @RequestBody JsonMessage jsonMessage) {
        ResponseMessage responseMessage = new ResponseMessage(jsonMessage);
        try {
            this.apiUsersService.loginOut(jsonMessage);
            responseMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            responseMessage.setMessage("退出成功!");
        } catch (Exception e) {
            CommonExceptionHandle.handleException(responseMessage, jsonMessage, request, e);
        }
        return responseMessage;
    }

    /**
     * 获取个人资料
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getUserInfo" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage getUserInfo(@RequestBody JsonMessage jsonMessage,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:获取用户信息
            UserInfoVo userInfoVo = this.apiUsersService.getUserInfo(jsonMessage);
            //step3:返回结果
            resMessage.addKey$Value("id", userInfoVo.getId());
            resMessage.addKey$Value("userTrueName", userInfoVo.getUserTrueName());
            resMessage.addKey$Value("userNickName", userInfoVo.getUserNickName());
            resMessage.addKey$Value("headPic", userInfoVo.getHeadPic());
            resMessage.addKey$Value("userGrenderWx", userInfoVo.getUserGrenderWx());
            resMessage.addKey$Value("provinceId", userInfoVo.getProvinceId());
            resMessage.addKey$Value("provinceName", userInfoVo.getProvinceName());
            resMessage.addKey$Value("cityId", userInfoVo.getCityId());
            resMessage.addKey$Value("cityName", userInfoVo.getCityName());
            resMessage.addKey$Value("hospitalName", userInfoVo.getHospitalName());
            resMessage.addKey$Value("departmentName", userInfoVo.getDepartmentName());
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 修改个人资料
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/updateUserInfo" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage updateUserInfo(@RequestBody JsonMessage jsonMessage,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:修改个人资料
            this.apiUsersService.updateUserInfo(jsonMessage);
            //step2:返回结果
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 验证当前用户是否填写个人信息
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/validateWriteUserInfo" }, consumes = { "application/json" }, produces = { "application/json" })
    //@LoginRequired
    public @ResponseBody ResponseMessage validateWriteUserInfo(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestBody JsonMessage jsonMessage) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            boolean result = this.apiUsersService.validateWriteUserInfo(jsonMessage);
            if (result) {
                resMessage.addKey$Value("isWrite", 1);
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            } else {
                resMessage.addKey$Value("isWrite", 0);
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            }
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

    /**
     * 查询省市区联动
     * @param jsonMessage
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = { "/getRegionList" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage getRegionList(@RequestBody JsonMessage jsonMessage,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:查询省市区信息
            List<RegionModel> regionList = this.apiUsersService.getRegionList(jsonMessage);
            //step2:返回结果
            resMessage.addBean("regionList", regionList);
            resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
            resMessage.setMessage(ResponseMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }
    
    /**
     * 公众号验证当前用户是否登录
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/validateWechatPublicUserLogin" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody ResponseMessage validateWechatPublicUserLogin(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody JsonMessage jsonMessage) {
        //返回对象
        ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            ResponseMessage responseMessage = ValidateLoginHelp.validateUserLogin(jsonMessage);
            if (responseMessage.getStatus() == ResponseMessage.SUCCESS_CODE) {
                resMessage.addKey$Value("isLogin", 1);
                resMessage.setMessage(responseMessage.getMessage());
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            } else {
                resMessage.addKey$Value("isLogin", 0);
                resMessage.setMessage(responseMessage.getMessage());
                resMessage.setStatus(ResponseMessage.SUCCESS_CODE);
                return resMessage;
            }
        } catch (Exception e) {
            CommonExceptionHandle.handleException(resMessage, jsonMessage, request, e);
        }
        return resMessage;
    }

}
