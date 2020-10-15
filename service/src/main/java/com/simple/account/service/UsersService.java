package com.simple.account.service;

import com.alibaba.fastjson.JSONObject;

import com.simple.account.vo.UserInfoVo;
import com.simple.common.api.GenericRequest;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.Authorize;
import com.simple.common.auth.Sessions;
import com.simple.common.error.ServiceHelper;
import com.simple.common.token.JwtUtils;
import com.simple.common.token.UserTokenHelp;
import com.simple.common.util.EmojiFilterUtil;
import com.simple.common.util.TokenProccessor;
import com.simple.common.util.WechatUtil;

import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;
import com.simple.common.token.DesPcTokenUtil;
import com.simple.common.token.DesTokenUtil;
import com.simple.common.error.ServiceException;
import com.simple.core.redis.JedisDBEnum;
import com.simple.core.redis.JedisHelper;

import com.simple.account.dao.UserDetailDao;
import com.simple.account.dao.UsersDao;
import com.simple.account.model.UserDetailModel;
import com.simple.account.model.UsersModel;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.ws.rs.core.Response;
import java.util.*;

/**
 * 用户管理业务层
 * @author hejinguo
 * @version $Id: UsersService.java, v 0.1 2020年7月25日 下午3:47:56
 */
@Service
@RequiredArgsConstructor
public class UsersService {
    private static final Logger     logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private UsersDao                usersDao;

    @Autowired
    private UserDetailDao           userDetailDao;


    private final PasswordEncoder passwordEncoder;

    /**
     * 普通用户登录或注册
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserInfoVo testUserInfo(int userid) throws Exception {
       return  this.usersDao.getUserInfo(userid);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean register(GenericRequest req) {

        //step1:获取全部请求参数并验证
        String username = req.getString("username");
        String password =  req.getString("password");
        Byte userGrender = req.getByte("userGrender");
        String userMobile = req.getString("phoneNumber");

        //step3:根据手机号查询用户信息
        //UsersModel usersModel = this.usersDao.selectUserByUserMobile(userMobile);
        UsersModel usersModel = this.usersDao.selectUserByUserLoginName(username);
        if (usersModel != null) {//重新生成token信息
            throw new ServiceException("该用户已存在");
        } else {
            System.out.println("input password is ------" + password);
            String pwHash = DesPcTokenUtil.encrypt(password);
            UsersModel newUser = UsersModel.builder().loginName(username).userNickName(username).userMobile(userMobile).passWord(pwHash).build();
            this.usersDao.insertUsers(newUser);
            //step5:创建用户明细
            UserDetailModel userDetail = UserDetailModel.builder().userId(newUser.getUserId())
                    .userGrenderWx(userGrender).build();
            this.usersDao.insertUserDetail(userDetail);
        }

//        //step:生成keyCloak 用户信息
//        if (!createOIDCAccount(username,password)){
//            throw new ServiceException("failed to create keycloak user info");
//        }
        return true;
    }

    public boolean createOIDCAccount(String username, String password) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://auth.e-healthcare.net/auth")
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
        RealmResource realmResource = keycloak.realm("healthcare");

        UserRepresentation user = new UserRepresentation();
        // 设置登录账号
        user.setUsername(username);
        // 设置账号“启用”
        user.setEnabled(true);
        //user.setEmail(request.getEmail());
        user.setFirstName("zhang");
        user.setLastName("yq");

        //set password
        List<CredentialRepresentation> credentials = new ArrayList<CredentialRepresentation>();
        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setType(CredentialRepresentation.PASSWORD);
        cr.setValue(password);
        cr.setTemporary(false);
        credentials.add(cr);
        user.setCredentials(credentials);

        //设置自定义用户属性
        Map<String, List<String>> attributes = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("音乐");
        list.add("美术");
        attributes.put("favorite", list);
        user.setAttributes(attributes);

        // 创建用户
        Response.StatusType result = realmResource.users().create(user).getStatusInfo();
        String resultText = result.getReasonPhrase() + String.valueOf(result.getStatusCode());
        if (result.getStatusCode() != 201) {
            System.out.println("the result of create user******************" + resultText);
            throw new ServiceException("failed to create new user!");
        }


        try {

            // 根据 username 查找用户
            UserRepresentation getUser = realmResource
                    .users()
                    .search(username)
                    .get(0);

            // 取得指定用户的 roleMappingResource
            RoleMappingResource roleMappingResource = realmResource
                    .users()
                    .get(getUser.getId())
                    .roles();

            // 为用户分配Realm角色

            RoleRepresentation realmRole = realmResource
                    .roles()
                    .get("operator")
                    .toRepresentation();

            roleMappingResource.realmLevel().add(Arrays.asList(realmRole));
        }catch(Exception ex){
            throw new ServiceException("failed to add role to new user!");
        }

        return true;
    }

    public String createOIDCAccessToken(String name, String password) {
        String accessToken = null;
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://auth.e-healthcare.net/auth")
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(name)
                    .password(password)
                    .clientId("health-manager")
                    .clientSecret("6ac03c55-3d4c-40dd-a89e-1adfe10fc9e0")
                    .realm("healthcare")
                    .resteasyClient(
                            new ResteasyClientBuilder()
                                    .connectionPoolSize(10).build()
                    )
                    .build();


            accessToken = keycloak.tokenManager().getAccessTokenString();

        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return accessToken;
    }
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
        String userNickName = EmojiFilterUtil.decode(jsonMessage.getString("userNickName"));
        String headPic = jsonMessage.getString("headPic");
        Byte userGrenderWx = jsonMessage.getByte("userGrenderWx");
        String userCountryWx = jsonMessage.getString("userCountryWx");
        String userProvinceWx = jsonMessage.getString("userProvinceWx");
        String userCityWx = jsonMessage.getString("userCityWx");
        String encryptedData = jsonMessage.getString("encryptedData");
        String iv = jsonMessage.getString("iv");
        String openId = jsonMessage.getString("openId");
        String unionId = jsonMessage.getString("unionId");
        String fromUserIdentity = jsonMessage.getString("fromUserIdentity");
        logger.error("fromUserIdentity--->{}",fromUserIdentity);
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

        //update by zhangyongqiao 2020-09-25
        //请求带有token，则直接清除
//        String token = jsonMessage.getToken();
//        if (StringUtils.isBlank(token)) {
//            JedisHelper.getInstance().del(token, JedisDBEnum.WECHAT);
//        }
        //step3:根据手机号查询用户信息
        UsersModel usersModel = this.usersDao.selectUserByUserMobile(userMobile);
        if (usersModel != null) {//重新生成token信息
            //验证用户状态
            byte userStatus = usersModel.getUserStatus();
            int userId = usersModel.getUserId();
            if (userStatus == 1) {
                logger.error("微信授权登录用户非正常状态,参数信息:userId--->{},userStatus--->{}", 
                    userId, userStatus);
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
                userToken,unionId, openId);
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
     * 获取用户信息
     * @param responseMessage
     * @return
     * @throws Exception
     */
    public UsersModel getUserInfo(ResponseMessage responseMessage) throws Exception {
        String token = "";//responseMessage.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("未获取到用户token信息");
        }
        int userId = UserTokenHelp.getPcUserId(token);
        UsersModel usersModel = usersDao.getUsersModel(userId);
        if (org.springframework.util.StringUtils.isEmpty(usersModel)) {
            throw new ServiceException("未查询到用户信息");
        }
        return usersModel;
    }

    /**
     * 用户退出登录
     * @param jsonMessage
     * @throws Exception
     */
    /*
    public void loginOut(JsonMessage jsonMessage) throws Exception {
        String openId = jsonMessage.getOpenId();
        String token = jsonMessage.getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("未获取到用户的token信息");
        }
        JedisHelper.getInstance().del(token, JedisDBEnum.WECHAT);
    }
    */


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String login(String username, String password) throws Exception {
        //返回信息
        UsersModel usersModel = this.usersDao.selectUserByUserLoginName(username);
        if (usersModel == null) {
            logger.error("用户信息不存在,参数信息:userName--->{}", username);
            throw new ServiceException("用户信息不存在!");
        }
        String userPassword=usersModel.getPassWord();
        String pwHash = DesPcTokenUtil.encrypt(password);
        System.out.println(userPassword + ":" + pwHash);
        if (!userPassword.equals(pwHash)) {
            logger.error("用户登录密码错误,参数信息:userName--->{}", username);
            throw new ServiceException("登录密码错误!");
        }
        Integer userId = usersModel.getUserId();
        String token = Sessions.createTokenWithUserInfo(userId, String.valueOf(userId), usersModel.getWechatOpenId(), "guest");
        return token;
    }
    /**
     * pc用户登录
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Map<String, Object> userLogin(JsonMessage jsonMessage) throws Exception {
        //返回信息
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //step1:获取全部请求参数并验证
        String userName = jsonMessage.getString("userName");
        String passWord = jsonMessage.getString("userPassword");
        UsersModel.validateUserLoginParam(userName, passWord);
        //step2:根据登录名查询用户信息
        UsersModel usersModel = this.usersDao.selectUserByUserLoginName(userName);
        if (usersModel == null) {
            logger.error("用户信息不存在,参数信息:userName--->{}", userName);
            throw new ServiceException("用户信息不存在!");
        }
        String userPassWord=usersModel.getPassWord();
        if (!DesPcTokenUtil.encrypt(passWord).equals(userPassWord)) {
            logger.error("用户登录密码错误,参数信息:userName--->{}", userName);
            throw new ServiceException("登录密码错误!");
        }
        if (usersModel.getUserType() != 2) {
            logger.error("该账户非系统账户,无法登录,参数信息:userName--->{}", userName);
            throw new ServiceException("该账户非系统账户,无法登录!");
        }
        //step3:重新生成新Token
        String oldToken = usersModel.getUserToken();
        //删除旧token
        if (StringUtils.isNotBlank(oldToken)) {
            JedisHelper.getInstance().del(oldToken, JedisDBEnum.PC);
        }
        //新token以及加密值
        String newToken = TokenProccessor.getInstance().makeToken();
        String value = DesPcTokenUtil.encrypt(usersModel.getUserId() + "," + newToken);
        //step4:修改用户资料信息
        Map<String, Object> userParamMap = new HashMap<String, Object>();
        userParamMap.put("userId", usersModel.getUserId());
        userParamMap.put("userToken", newToken);
        this.usersDao.updateUserToken(userParamMap);
        //step5:存入到redis
        JedisHelper.getInstance().set(newToken, value, JedisDBEnum.PC);
        //step6:返回信息
        returnMap.put("token", newToken);
        returnMap.put("newUserMessage", "登录成功！");
        return returnMap;
    }

    /**
     * 获取主页菜单列表
     * @param jsonMessage
     * @return
     * @throws Exception
     */
    /*
    public Map<String, Object> getUserMenu(JsonMessage jsonMessage) throws Exception {
        //返回对象
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //step1:获取用户ID
        int userId = UserTokenHelp.getPcUserId(jsonMessage.getToken());
        //step2:查询用户信息
        UsersModel usersModel = this.usersDao.getUsersModel(userId);
        UsersModel.validateUserStatus(usersModel);
        //step3:查询菜单信息
        Long id = 1L;//功能树顶级ID
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("isUnable", 1);//是否查询出已被禁用的功能 0：否 1：是
        //根据员工权限获取员工功能
        List<NodeVO> functions = this.usersDao.selectAllMenuById(map);
        //根据ID查询当前功能
        NodeVO node = this.usersDao.selectMenuById(id);
        //封装菜单树json
        MenuTreeVO tree = new MenuTreeVO();
        tree.getTree(functions, node, false);
        //step4:返回信息
        returnMap.put("id", usersModel.getUserId());
        returnMap.put("userTrueName", usersModel.getUserTrueName());
        returnMap.put("headPic", usersModel.getHeadPic());
        returnMap.put("menuList", tree.modifyStr(tree.returnStr.toString()));
        return returnMap;
    }

     */

}
