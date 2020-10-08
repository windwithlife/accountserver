package com.simple.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户明细实体
 * @author hejinguo
 * @version $Id: UserDetailModel.java, v 0.1 2020年7月18日 下午3:43:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_detail")
public class UserDetailModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 3234637081498537809L;
    /**用户详情ID*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer           userDetailId;
    /**用户ID*/
    private Integer           userId;
    /**用户性别(0:未知 1:男 2:女)*/
    private Byte              userGrenderWx;
    /**国家(微信)*/
    private String            userCountryWx;
    /**省或地区(微信)*/
    private String            userProvinceWx;
    /**城市(微信)*/
    private String            userCityWx;
    /**门店名称*/
    private String            storeName;
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
    private String            description;
    /**医院名称*/
    private String            hospitalName;
    /**职位名称*/
    private String            positionName;
    /**科室名称*/
    private String            departmentName;
    /**创建人*/
    private String            createdName;
    /**创建时间*/
    private Date              createdDate;
    /**修改人*/
    private String            updatedName;
    /**修改时间*/
    private Date              updatedDate;
    
    /**
     * 封装用户明细信息
     * @param userId
     * @param userGrenderWx
     * @param userCountryWx
     * @param userProvinceWx
     * @param userCityWx
     * @param userMobile
     * @return
     */
    public static UserDetailModel  createUserDetailModel(Integer userId,Byte userGrenderWx,String userCountryWx,String userProvinceWx,String userCityWx,String  userMobile){
        UserDetailModel userDetail = new UserDetailModel();
        userDetail.setUserId(userId);
        userDetail.setUserGrenderWx(userGrenderWx);
        userDetail.setUserCountryWx(userCountryWx);
        userDetail.setUserProvinceWx(userProvinceWx);
        userDetail.setUserCityWx(userCityWx);
        userDetail.setCreatedName(userMobile);
        return userDetail;
    } 

//    public Integer getUserDetailId() {
//        return userDetailId;
//    }
//
//    public void setUserDetailId(Integer userDetailId) {
//        this.userDetailId = userDetailId;
//    }
////
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

//    public Byte getUserGrenderWx() {
//        return userGrenderWx;
//    }
//
//    public void setUserGrenderWx(Byte userGrenderWx) {
//        this.userGrenderWx = userGrenderWx;
//    }
//
//    public String getUserCountryWx() {
//        return userCountryWx;
//    }
//
//    public void setUserCountryWx(String userCountryWx) {
//        this.userCountryWx = userCountryWx;
//    }
//
//    public String getUserProvinceWx() {
//        return userProvinceWx;
//    }
//
//    public void setUserProvinceWx(String userProvinceWx) {
//        this.userProvinceWx = userProvinceWx;
//    }
//
//    public String getUserCityWx() {
//        return userCityWx;
//    }
//
//    public void setUserCityWx(String userCityWx) {
//        this.userCityWx = userCityWx;
//    }
//
//    public String getStoreName() {
//        return storeName;
//    }
//
//    public void setStoreName(String storeName) {
//        this.storeName = storeName;
//    }
//
//    public Integer getProvinceId() {
//        return provinceId;
//    }
//
//    public void setProvinceId(Integer provinceId) {
//        this.provinceId = provinceId;
//    }
//
//    public String getProvinceName() {
//        return provinceName;
//    }
//
//    public void setProvinceName(String provinceName) {
//        this.provinceName = provinceName;
//    }
//
//    public Integer getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(Integer cityId) {
//        this.cityId = cityId;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getCreatedName() {
//        return createdName;
//    }
//
//    public void setCreatedName(String createdName) {
//        this.createdName = createdName;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getUpdatedName() {
//        return updatedName;
//    }
//
//    public void setUpdatedName(String updatedName) {
//        this.updatedName = updatedName;
//    }
//
//    public Date getUpdatedDate() {
//        return updatedDate;
//    }
//
//    public void setUpdatedDate(Date updatedDate) {
//        this.updatedDate = updatedDate;
//    }

//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }

//    public String getHospitalName() {
//        return hospitalName;
//    }
//
//    public void setHospitalName(String hospitalName) {
//        this.hospitalName = hospitalName;
//    }
//
//    public String getPositionName() {
//        return positionName;
//    }
//
//    public void setPositionName(String positionName) {
//        this.positionName = positionName;
//    }
//
//    public String getDepartmentName() {
//        return departmentName;
//    }
//
//    public void setDepartmentName(String departmentName) {
//        this.departmentName = departmentName;
//    }

}
