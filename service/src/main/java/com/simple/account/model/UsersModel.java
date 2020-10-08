package com.simple.account.model;

import com.simple.common.error.ServiceException;
import com.simple.account.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author hejinguo
 * @version $Id: UsersModel.java, v 0.1 2020年7月13日 下午3:15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UsersModel implements Serializable {
    private static final Logger logger              = LoggerFactory.getLogger(UsersModel.class);
    /**  */
    private static final long   serialVersionUID    = -3299069399674355478L;

    /**用户状态(0:可用)*/
    public static final Byte    USER_STATUS_USABLE  = 0;
    /**用户状态(1:禁用)*/
    public static final Byte    USER_STATUS_DISABLE = 1;

    /**用户ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer             userId;
    /**用户昵称*/
    private String              userNickName;
    /**用户真实姓名*/
    private String              userTrueName;
    /**登录名*/
    private String              loginName;
    /**用户密码*/
    private String              passWord;
    /**用户手机号*/
    private String              userMobile;
    /**用户头像*/
    private String              headPic;
    /**小程序openID*/
    private String              wechatOpenId;
    /**公众号openId*/
    private String              wechatPublicOpenId;
    /**微信unionid*/
    private String              unionId;
    /**用户token*/
    private String              userToken;
    /**用户类型(0:用户 1:讲师 2:管理员)*/
    private Integer             userType;
    /**用户状态(0:可用  1:禁用)*/
    private Byte                userStatus;
    /**版本号*/
    private Integer             version;
    /**创建人*/
    private String              createdName;
    /**创建时间*/
    private Date                createdDate;
    /**修改人*/
    private String              updatedName;
    /**修改时间*/
    private Date                updatedDate;

    /**
     * 用户状态验证
     * @param usersModel
     */
    public static void validateUserStatus(UsersModel usersModel) {
        if (usersModel == null) {
            throw new ServiceException("用户信息不存在!");
        }
        if (usersModel.getUserStatus() != 0) {
            throw new ServiceException("当前用户非正常状态!");
        }
    }

    /**
     * 用户注册参数验证
     * @param jsonMessage
     * @throws Exception
     */
    public static void validateRegistUserParam(String userNickName, String headPic,
                                               Byte userGrenderWx, String encryptedData, String iv,
                                               String openId) throws Exception {
        if (StringUtils.isBlank(userNickName)) {
            throw new ServiceException("用户昵称不能为空!");
        }
        if (StringUtils.isBlank(headPic)) {
            throw new ServiceException("用户头像不能为空!");
        }
        if (userGrenderWx == null) {
            throw new ServiceException("用户性别不能为空!");
        }
        if (StringUtils.isBlank(encryptedData)) {
            logger.error("用户注册时获取手机号授权登录信息为空,参数信息：encryptedData--->{}", encryptedData);
            throw new ServiceException("微信信息获取失败,请重新授权登录!");
        }
        if (StringUtils.isBlank(iv)) {
            logger.error("用户注册时获取手机号授权登录信息为空,参数信息：iv--->{}", iv);
            throw new ServiceException("微信信息获取失败,请重新授权登录!");
        }
        if (StringUtils.isBlank(openId)) {
            logger.error("用户注册时获取手机号授权登录信息为空,参数信息：openId--->{}", openId);
            throw new ServiceException("微信信息获取失败,请重新授权登录!");
        }
    }

    /**
     * 修改用户信息参数验证
     * @param userInfoVo
     * @throws Exception
     */
    public static void validateUpdateUserInfoParam(UserInfoVo userInfoVo) throws Exception {
        if (userInfoVo == null) {
            throw new ServiceException("请求参数信息不能为空!");
        }
        if (StringUtils.isBlank(userInfoVo.getUserTrueName())) {
            throw new ServiceException("用户姓名不能为空!");
        }
        if (StringUtils.isBlank(userInfoVo.getHospitalName())) {
            throw new ServiceException("医院名称不能为空!");
        }
        if (StringUtils.isBlank(userInfoVo.getDepartmentName())) {
            throw new ServiceException("科室不能为空!");
        }
        if (StringUtils.isBlank(userInfoVo.getProvinceName())) {
            throw new ServiceException("省份不能为空!");
        }
        //        if (StringUtils.isBlank(userInfoVo.getCityName())) {
        //            throw new ServiceException("城市不能为空!");
        //        }
    }

    /**
     * pc用户登录参数验证
     * @param userName
     * @param passWord
     * @throws Exception
     */
    public static void validateUserLoginParam(String userName, String passWord) throws Exception {
        if (StringUtils.isBlank(userName)) {
            throw new ServiceException("登录用户名不能为空!");
        }
        if (StringUtils.isBlank(passWord)) {
            throw new ServiceException("登录密码不能为空!");
        }
    }

    /**
     * 微信公众号授权登录或注册参数验证
     * @param jsonMessage
     * @throws Exception
     */
    public static void validateRegisterWechatPublicUserParam(String code) throws Exception {
        if (StringUtils.isBlank(code)) {
            logger.error("微信公众号授权登录或注册时code不能为空,参数信息：code--->{}",
                code);
            throw new ServiceException("请求参数错误,请重新授权登录!");
        }
    }

    /**
     * 封装新用户信息
     * @param userNickName 用户昵称
     * @param userMobile 手机号
     * @param headPic  用户头像
     * @param token  
     * @param openId
     * @return
     */
    public static UsersModel createUsersModel(String userNickName, String userMobile,
                                              String headPic, String token,String unionId, String openId) {
        UsersModel user = new UsersModel();
        user.setUserNickName(userNickName);
        user.setLoginName(userMobile);
        user.setUserMobile(userMobile);
        user.setHeadPic(headPic);
        user.setWechatOpenId(openId);
        user.setUserToken(token);
        user.setUnionId(unionId);
        user.setUserStatus(UsersModel.USER_STATUS_USABLE);
        user.setCreatedName(userMobile);
        return user;
    }
/*
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getWechatPublicOpenId() {
        return wechatPublicOpenId;
    }

    public void setWechatPublicOpenId(String wechatPublicOpenId) {
        this.wechatPublicOpenId = wechatPublicOpenId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
  */
}
