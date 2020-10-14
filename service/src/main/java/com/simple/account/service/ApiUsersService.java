package com.simple.account.service;

import com.alibaba.fastjson.JSONObject;
import com.simple.common.util.TokenProccessor;
import com.simple.common.util.WechatUtil;
import com.simple.core.data.request.JsonMessage;
import com.simple.common.token.DesTokenUtil;
import com.simple.common.error.ServiceException;
import com.simple.core.redis.JedisDBEnum;
import com.simple.core.redis.JedisHelper;
import com.simple.common.token.UserTokenHelp;
import com.simple.core.wechat.AdvancedUtil;
import com.simple.core.wechat.beans.oauth2.Oauth2AccessToken;
import com.simple.core.wechat.beans.oauth2.WechatUserInfo;
import com.simple.account.dao.UserDetailDao;
import com.simple.account.dao.UsersDao;
import com.simple.account.model.RegionModel;
import com.simple.account.model.UserDetailModel;
import com.simple.account.model.UsersModel;
import com.simple.account.vo.UserInfoVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理业务层
 * @author hejinguo
 * @version $Id: UsersService.java, v 0.1 2019年11月17日 下午9:51:48
 */
@Service
public class ApiUsersService {
    private static final Logger logger = LoggerFactory.getLogger(ApiUsersService.class);
    @Autowired
    private UsersDao            usersDao;
    @Autowired
    private UserDetailDao       userDetailDao;

    /**
     * 用户登录或注册
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Map<String, Object> registerUser(JsonMessage jsonMessage) throws Exception {
        //返回信息
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //step1:获取全部请求参数并验证
        String userNickName = jsonMessage.getString("userNickName");// EmojiFilterUtil.decode(jsonMessage.getString("userNickName"));
        String headPic = jsonMessage.getString("headPic");
        Byte userGrenderWx = jsonMessage.getByte("userGrenderWx");
        String userCountryWx = jsonMessage.getString("userCountryWx");
        String userProvinceWx = jsonMessage.getString("userProvinceWx");
        String userCityWx = jsonMessage.getString("userCityWx");
        String encryptedData = jsonMessage.getString("encryptedData");
        String iv = jsonMessage.getString("iv");
        String openId = jsonMessage.getString("openId");
        String unionId = jsonMessage.getString("unionId");
        UsersModel.validateRegistUserParam(userNickName, headPic, userGrenderWx, encryptedData, iv,
            openId);
        //step2:获取手机号授权信息
        String sessionKey = JedisHelper.getInstance().get(openId, JedisDBEnum.WECHAT);
        if (StringUtils.isBlank(sessionKey)) {
            logger.error("微信授权登录获取信息会话秘钥为空,参数信息:sessionKey--->{}", sessionKey);
            throw new ServiceException("微信授权登录获取信息失败,请重新授权登录!");
        }
        JSONObject jsonObj = WechatUtil.decrypt(encryptedData, sessionKey, iv);
        String userMobile = jsonObj.getString("phoneNumber");
        if (StringUtils.isBlank(userMobile)) {
            logger.error("微信授权登录获取授权手机号为空,参数信息:userMobile--->{}", userMobile);
            throw new ServiceException("微信授权登录获取信息失败,请重新授权登录!");
        }
        //请求带有token，则直接清除
        String token = "";//jsonMessage.getToken();
        if (StringUtils.isNotBlank(token)) {
            JedisHelper.getInstance().del(token, JedisDBEnum.WECHAT);
        }
        //step3:根据手机号查询用户信息
        UsersModel usersModel = this.usersDao.selectUserByUserMobile(userMobile);
        if (usersModel != null) {//重新生成token信息
            //验证用户状态
            byte userStatus = usersModel.getUserStatus();
            int userId = usersModel.getUserId();
            if (userStatus == 1) {
                logger.error("微信授权登录用户非正常状态,参数信息:userId--->{},userStatus--->{}", userId,
                    userStatus);
                throw new ServiceException("当前账户非正常状态!");
            }
            String oldToken = usersModel.getUserToken();
            //删除旧token
            if (StringUtils.isNotBlank(oldToken)) {
                JedisHelper.getInstance().del(oldToken, JedisDBEnum.WECHAT);
            }
            //新token以及加密值
            String newToken = TokenProccessor.getInstance().makeToken();
            String value = DesTokenUtil.encrypt(usersModel.getUserId() + "," + newToken);
            //step4:修改用户资料信息
            Map<String, Object> userParamMap = new HashMap<String, Object>();
            userParamMap.put("userId", userId);
            userParamMap.put("openId", openId);
            userParamMap.put("userToken", newToken);
            this.usersDao.updateUserToken(userParamMap);

            Map<String, Object> usersMap = new HashMap<String, Object>();
            usersMap.put("userId", userId);
            usersMap.put("headPic", headPic);
            usersMap.put("userNickName", userNickName);
            usersMap.put("updatedName", userNickName);
            this.usersDao.updateUserNickName(usersMap);
            //step5:存入到redis
            JedisHelper.getInstance().set(newToken, value, JedisDBEnum.WECHAT);
            //step6:返回信息
            returnMap.put("openId", openId);
            returnMap.put("token", newToken);
            returnMap.put("newUserMessage", "登录成功！");
        } else {
            //step4:创建用户资料信息
            String userToken = TokenProccessor.getInstance().makeToken();//用户token
            UsersModel user = UsersModel.createUsersModel(userNickName, userMobile, headPic,
                userToken, unionId, openId);
            this.usersDao.insertUsers(user);
            //step5:创建用户明细
            UserDetailModel userDetail = UserDetailModel.createUserDetailModel(user.getUserId(),
                userGrenderWx, userCountryWx, userProvinceWx, userCityWx, userMobile);
            this.usersDao.insertUserDetail(userDetail);
            //step8:存入到redis
            String value = DesTokenUtil.encrypt(user.getUserId() + "," + userToken);
            JedisHelper.getInstance().set(userToken, value, JedisDBEnum.WECHAT);
            //step9:返回信息
            returnMap.put("openId", openId);
            returnMap.put("token", userToken);
            returnMap.put("isNewUser", 1);
            returnMap.put("newUserMessage", "登录成功！");
        }
        return returnMap;
    }

    /**
     * 用户退出登录
     * @param jsonMessage
     * @throws Exception
     */
    public void loginOut(JsonMessage jsonMessage) throws Exception {
        String openId = "";//jsonMessage.getOpenId();
        String token = "";//jsonMessage.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("未获取到用户的token信息");
        }
        JedisHelper.getInstance().del(token, JedisDBEnum.WECHAT);
    }

    /**
     * 获取个人用户信息
     * @return
     * @throws Exception
     */
    public UserInfoVo getUserInfo(JsonMessage jsonMessage) throws Exception {
        String token = "";//jsonMessage.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("未获取到用户token信息");
        }
        int userId = UserTokenHelp.getWechatUserId(token);
        UserInfoVo userInfoVo = this.usersDao.getUserInfo(userId);
        return userInfoVo;
    }

    /**
     * 修改个人信息
     * @param jsonMessage
     * @throws Exception
     */
    public void updateUserInfo(JsonMessage jsonMessage) throws Exception {
        //step1:信息转换
        String token = "";//jsonMessage.getToken();
        int userId = UserTokenHelp.getWechatUserId(token);
        UserInfoVo userInfoVo = JSONObject.toJavaObject(jsonMessage.getData(), UserInfoVo.class);
        userInfoVo.setId(userId);
        UsersModel.validateUpdateUserInfoParam(userInfoVo);
        //step2:修改用户及明细信息
        UsersModel usersModel = this.usersDao.getUsersModel(userInfoVo.getId());
        if (usersModel == null) {
            throw new ServiceException("用户信息不存在!");
        }
        this.usersDao.updateUserInfo(userInfoVo);
        //step3:修改用户明细信息
        this.userDetailDao.updateUserDetailInfo(userInfoVo);
    }

    /**
     * 验证当前用户是否填写个人信息
     * @param jsonMessage
     * @return
     */
    public boolean validateWriteUserInfo(JsonMessage jsonMessage) {
        boolean result = false;
        String token = "";//jsonMessage.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("未获取到用户token信息");
        }
        int userId = UserTokenHelp.getWechatUserId(token);
        //step1:查询用户信息
        UsersModel usersModel = this.usersDao.getUsersModel(userId);
        UsersModel.validateUserStatus(usersModel);
        //step2:查询用户明细信息
        UserDetailModel userDetailModel = this.userDetailDao.getUserDetailInfo(userId);
        if (userDetailModel == null) {
            return result;
        }
        //step3:验证信息
        String userTrueName = usersModel.getUserTrueName();
        String provinceName = userDetailModel.getProvinceName();
        // String cityName=userDetailModel.getCityName();
        String hospitalName = userDetailModel.getHospitalName();
        String departmentName = userDetailModel.getDepartmentName();
        if (StringUtils.isBlank(userTrueName) || StringUtils.isBlank(provinceName)
            || StringUtils.isBlank(hospitalName) || StringUtils.isBlank(departmentName)) {
            return result;
        }
        return true;
    }

    /**
     * 省市区查询
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    public List<RegionModel> getRegionList(JsonMessage jsonMessage) throws Exception {
        //step1:获取请求参数
        Integer pid = jsonMessage.getInteger("pid");
        Integer level = jsonMessage.getInteger("level");
        if (pid == null || pid == 0) {
            pid = 100000;
            level = 1;
        }
        //step2:根据pid查询地区信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pid", pid);
        paramMap.put("level", level);
        List<RegionModel> regionList = this.usersDao.getRegionList(paramMap);
        return regionList;

    }

    /**
    * 微信公众号授权登录或注册
    * @param jsonMessage
    * @return
    * @throws Exception
    */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Map<String, Object> registerWechatPublicUser(JsonMessage jsonMessage) throws Exception {
        //返回信息
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //step1:获取全部请求参数并验证
        String code = jsonMessage.getString("code");
        UsersModel.validateRegisterWechatPublicUserParam(code);
        //step2:获取微信用户信息
        WechatUserInfo userInfo = null;
        Oauth2AccessToken accessToken = AdvancedUtil.getOauth2AccessToken(code);
        if (accessToken == null) {
            logger.error("获取公众号accessToken返回为空,请重新打开页面,参数信息:code--->{}", code);
            throw new ServiceException("微信授权登录失败,请重新打开页面!");
        }
        String openId = accessToken.getOpenId();
        String accessTokenStr = accessToken.getAccessToken();
        if (StringUtils.isBlank(openId)) {
            logger.error("获取微信ID失败,请重新打开页面,参数信息:code--->{},openId--->{}", code, openId);
            throw new ServiceException("微信授权登录失败,请重新打开页面!");
        }
        if (StringUtils.isBlank(accessTokenStr)) {
            logger.error("获取微信accessToken失败,请重新打开页面,参数信息:openId--->{},code--->{}", openId,
                accessTokenStr);
            throw new ServiceException("微信授权登录失败,请重新打开页面!");
        }
        //step3:根据accessToken和openId获取用户信息
        userInfo = AdvancedUtil.getOauthUserInfo(accessTokenStr, openId);
        if (userInfo == null) {
            logger.error("获取公众号授权用户信息返回为空,参数信息:accessToken--->{},openId--->{}", accessTokenStr,
                openId);
            throw new ServiceException("微信授权登录失败,请重新打开页面!");
        }
        //step4:请求带有token，则直接清除
        String token = "";//jsonMessage.getToken();
        if (StringUtils.isNotBlank(token)) {
            JedisHelper.getInstance().del(token, JedisDBEnum.WECHAT);
        }
        //step3:根据手机号查询用户信息
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("unionId", userInfo == null ? null : userInfo.getUnionid());
        paraMap.put("openId", openId);
        UsersModel usersModel = this.usersDao.selectUserByunionIdOrOpenId(paraMap);

        if (usersModel != null) {//重新生成token信息
            //验证用户状态
            byte userStatus = usersModel.getUserStatus();
            int userId = usersModel.getUserId();
            if (userStatus == 1) {
                logger.error("微信授权登录用户非正常状态,参数信息:userId--->{},userStatus--->{}", userId,
                    userStatus);
                throw new ServiceException("当前账户非正常状态!");
            }
            String oldToken = usersModel.getUserToken();
            //删除旧token
            if (StringUtils.isNotBlank(oldToken)) {
                JedisHelper.getInstance().del(oldToken, JedisDBEnum.WECHAT);
            }
            //新token以及加密值
            String newToken = TokenProccessor.getInstance().makeToken();
            String value = DesTokenUtil.encrypt(usersModel.getUserId() + "," + newToken);
            //step4:修改用户资料信息
            Map<String, Object> userParamMap = new HashMap<String, Object>();
            userParamMap.put("userId", userId);
            userParamMap.put("userToken", newToken);
            this.usersDao.updateUserToken(userParamMap);
            if (userInfo != null) {
                Map<String, Object> usersMap = new HashMap<String, Object>();
                usersMap.put("userId", userId);
                usersMap.put("headPic", userInfo.getHeadimgurl());
                usersMap.put("userNickName", userInfo.getNickname());
                usersMap.put("updatedName", userInfo.getNickname());
                this.usersDao.updateUserNickName(usersMap);
            }
            //step5:存入到redis
            JedisHelper.getInstance().set(newToken, value, JedisDBEnum.WECHAT);
            //step6:返回信息
            returnMap.put("openId", openId);
            returnMap.put("token", newToken);
        } else {
            //step4:创建用户资料信息
            String userToken = TokenProccessor.getInstance().makeToken();//用户token
            UsersModel user = UsersModel.createUsersModel(userInfo.getNickname(), null,
                userInfo.getHeadimgurl(), userToken, null, userInfo.getUnionid());
            user.setWechatPublicOpenId(openId);
            this.usersDao.insertUsers(user);
            //step5:创建用户明细
            UserDetailModel userDetail = UserDetailModel.createUserDetailModel(user.getUserId(),
                userInfo.getSex().byteValue(), userInfo.getCountry(), userInfo.getProvince(),
                userInfo.getCity(), null);
            this.usersDao.insertUserDetail(userDetail);
            //step8:存入到redis
            String value = DesTokenUtil.encrypt(user.getUserId() + "," + userToken);
            JedisHelper.getInstance().set(userToken, value, JedisDBEnum.WECHAT);
            //step9:返回信息
            returnMap.put("openId", openId);
            returnMap.put("token", userToken);
        }
        return returnMap;
    }

}
