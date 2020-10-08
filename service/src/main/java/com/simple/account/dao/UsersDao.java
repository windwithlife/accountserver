/**
 * DianMei.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package com.simple.account.dao;

import com.simple.account.model.RegionModel;
import com.simple.account.model.UserDetailModel;
import com.simple.account.model.UsersModel;
import com.simple.account.vo.NodeVO;
import com.simple.account.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户管理数据层接口
 * @author hejinguo
 * @version $Id: PaymentRecordDao.java, v 0.1 2019年11月17日 下午9:26:50
 */
@Mapper
public interface UsersDao {
    /**
     * 根据手机号查询用户信息
     * @param userMobile
     * @return
     */
    UsersModel selectUserByUserMobile(@Param("userMobile") String userMobile);

    /**
     * 用户登录更新用户token
     * @param paramMap
     */
    void updateUserToken(Map<String, Object> paramMap);

    /**
     * 新增用户信息
     * @param user
     */
    void insertUsers(UsersModel user);

    /**
     * 新增用户明细
     * @param userDetail
     */
    void insertUserDetail(UserDetailModel userDetail);

    /**
     * 修改用户昵称头像
     * @param paramMap
     */
    void updateUserNickName(Map<String, Object> paramMap);

    /**
     * 根据用户的ID获取用户的对象信息
     * @author G
     * @param userId
     * @return
     */
    UsersModel getUsersModel(int userId);

    /**
     * 根据ID查询用户信息
     * @param userId
     * @return
     */
    UserInfoVo getUserInfo(@Param("userId") int userId);

    /**
     * 修改用户信息
     * @param userInfoVo
     */
    void updateUserInfo(UserInfoVo userInfoVo);

    /**
     * 根据登录用户名查询用户信息
     * @param userName
     * @return
     */
    UsersModel selectUserByUserLoginName(@Param("userName") String userName);

    /**
     * 查询全部菜单信息
     * @param paramMap
     * @return
     */
    List<NodeVO> selectAllMenuById(Map<String, Object> paramMap);

    /**
     * 根据ID查询菜单信息
     * @param id
     * @return
     */
    NodeVO selectMenuById(@Param("id") long id);
    
    /**
     * 根据pid查询地区信息
     * @param paramMap
     * @return
     */
    List<RegionModel> getRegionList(Map<String, Object> paramMap);
    
    /**
     * 根据unionId或openid查询公众号用户信息
     * @param userMobile
     * @return
     */
    UsersModel selectUserByunionIdOrOpenId(Map<String, String> paramMap);
}
