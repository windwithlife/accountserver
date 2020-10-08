package com.simple.account.vo;

import com.simple.common.api.BaseResponse;

import java.io.Serializable;

/**
 * 用户实体VO
 * @author hejinguo
 * @version $Id: UserInfoVo.java, v 0.1 2020年7月25日 下午2:04:56
 */
public class UserInfoVo  implements Serializable  {
    /**  */
    private static final long serialVersionUID = 5221642423407429049L;
    /**用户ID*/
    private Integer           id;
    /**用户昵称*/
    private String            userNickName;
    /**用户真实姓名*/
    private String            userTrueName;
    /**登录名*/
    private String            loginName;
    /**用户手机号*/
    private String            userMobile;
    /**用户头像*/
    private String            headPic;
    /**小程序openID*/
    private String            wechatOpenId;
    /**公众号openId*/
    private String            wechatPublicOpenId;
    /**微信unionid*/
    private String            unionId;
    /**用户token*/
    private String            userToken;
    /**用户类型(0:用户 1:讲师 2:管理员)*/
    private Integer           userType;
    /**用户状态(0:可用  1:禁用)*/
    private Integer           userStatus;

    /**用户性别(0:未知 1:男 2:女)*/
    private Integer           userGrenderWx;
    /**省或地区ID*/
    private Integer           provinceId;
    /**省或地区名称*/
    private String            provinceName;
    /**城市ID*/
    private Integer           cityId;
    /**城市名称*/
    private String            cityName;
    /**详细地址*/
    private String            address;
    /**讲师简介*/
    private String            desc;
    /**医院名称*/
    private String            hospitalName;
    /**职位名称*/
    private String            positionName;
    /**科室名称*/
    private String            departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserGrenderWx() {
        return userGrenderWx;
    }

    public void setUserGrenderWx(Integer userGrenderWx) {
        this.userGrenderWx = userGrenderWx;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
   
}
