package com.simple.account.controller;

import com.simple.account.service.ApiUsersService;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.LoginRequired;
import com.simple.common.auth.Sessions;
import com.simple.common.controller.BaseController;
import com.simple.common.error.ServiceHelper;
import com.simple.account.model.RegionModel;
import com.simple.account.vo.UserInfoVo;
import com.simple.common.props.AppProps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("api/userService")
public class ApiUsersController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiUsersController.class);
    @Autowired
    private ApiUsersService apiUsersService;

    @Autowired
    private AppProps appProps;
    /**
     * 验证当前用户是否登录
     * @param jsonMessage
     * @return
     */
    @PostMapping(value = { "/validateUserLogin" }, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody GenericResponse validateUserLogin(HttpServletRequest request,
                                                           GenericRequest req) {

        try {
            if (Sessions.validateAuthentication(request)){
                return GenericResponse.build().addKey$Value("isLogin", 1);
            }else{
                return GenericResponse.build().addKey$Value("isLogin", 0);
            }

        } catch (Exception e) {
            this.handleExeption(e,"failed to validateUserLogin");
            return GenericResponse.build().addKey$Value("isLogin", 0);
        }

    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @PostMapping(value = {"/loginOut"}, consumes = {"application/json"}, produces = {"application/json"})
    @LoginRequired
    public @ResponseBody
    BaseResponse logOut(@RequestBody GenericRequest req,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        try {
            String domain = req.getString("domainName");
            if (StringUtils.isBlank(domain)){
                domain = appProps.getDomainName();
            }
            Sessions.logout(domain, request, response);
            return GenericResponse.build().message("退出成功!");
        } catch (Exception e) {
            return ServiceHelper.handleControllerException(e, "failed to logout");
        }

    }

    /**
     * 获取个人资料
     *
     * @return
     */
    @PostMapping(value = {"/getUserInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    @LoginRequired
    public @ResponseBody
    GenericResponse getUserInfo(@RequestBody GenericRequest req,
                             HttpServletRequest request) {
        //返回对象
        GenericResponse res = GenericResponse.build();
        //ResponseMessage resMessage = new ResponseMessage(jsonMessage);
        try {
            //step1:获取用户信息
            String token = Sessions.getAuthToken(request);
            int userId = Sessions.getSessionUserInfo(token).getId();
            UserInfoVo userInfoVo = this.apiUsersService.getUserInfo(userId);
            res.setDataObject(userInfoVo);
            return res;
        } catch (Exception e) {
            return ServiceHelper.handleControllerException(e, "failed to get userInfo");
        }
    }

    /**
     * 修改个人资料
     *
     * @return
     */
    @PostMapping(value = {"/updateUserInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    @LoginRequired
    public @ResponseBody
    BaseResponse updateUserInfo(@RequestBody GenericRequest req,
                                HttpServletRequest request) {
        try {
            //step1:修改个人资料
            UserInfoVo userInfo = req.getObject(UserInfoVo.class);
            int userId = Sessions.getSessionUserInfo(Sessions.getAuthToken(request)).getId();
            userInfo.setId(userId);
            this.apiUsersService.updateUserInfo(userInfo);
            return GenericResponse.build().message("successful to update user info");
        } catch (Exception e) {
            return this.handleExeption(e, "failed to update user info");
        }

    }

    /**
     * 验证当前用户是否填写个人信息
     *
     * @return
     */
    @PostMapping(value = {"/validateWriteUserInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    //@LoginRequired
    public @ResponseBody
    GenericResponse validateWriteUserInfo(@RequestBody GenericRequest req,
                                       HttpServletRequest request) {

        try {
            int userId = Sessions.getSessionUserInfo(Sessions.getAuthToken(request)).getId();
            boolean result = this.apiUsersService.validateWriteUserInfo(userId);
            if (result) {
                return GenericResponse.build().addKey$Value("isWrite", 1);
            } else {
                return GenericResponse.build().addKey$Value("isWrite", 0);
            }
        } catch (Exception e) {
            handleExeption(e, "failed to valid user info");
            return GenericResponse.build().addKey$Value("isWrite", 0);
        }

    }

    /**
     * 查询省市区联动
     *
     * @param request
     * @return
     */
    @PostMapping(value = {"/getRegionList"}, consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    BaseResponse getRegionList(@RequestBody GenericRequest req) {
        Integer pid = req.getInteger("pid");
        Integer level = req.getInteger("level");

        try {
            //step1:查询省市区信息
            List<RegionModel> regionList = this.apiUsersService.getRegionList(pid, level);
            //step2:返回结果
            return GenericResponse.build().addKey$Value("regionList", regionList);

        } catch (Exception e) {
            return this.handleExeption(e, "failed to get Region List");
        }

    }


    @PostMapping(value = {"/validateWechatPublicUserLogin"}, consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    BaseResponse testVerify(@RequestHeader(AuthConstant.AUTHENTICATION_HEADER) String token,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        GenericResponse res = GenericResponse.build();
        try {

            if (Sessions.validateToken(token)) {
                res.addKey$Value("isLogin", 1);
            } else {
                res.addKey$Value("isLogin", 0);
            }
            ;
            return res;
        } catch (Exception ex) {
            this.handleExeption(ex, "failed to verify token");
            res.addKey$Value("isLogin", 0);
            return res;
        }
    }

}