package com.simple.account.dao;

import com.simple.account.dto.AdvertVO;
import com.simple.account.dto.BannerVO;
import com.simple.account.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface AccountDao {

    void addAccount(Account account) throws Exception;
    Account findByName(String name) throws Exception;
//    List<AdvertVO> getInformationList() throws Exception;
//
//    AdvertVO getInformationDetail(@Param("id") Integer id) throws Exception;
//
//    int getAdvertListCount(Map<String, Object> paramMap) throws Exception;
//
//    List<BannerVO> getAdvertList(Map<String, Object> paramMap) throws Exception;
//
//
//
//    LiveAdvertModel getLiveAdvertById(@Param("id") Integer id) throws Exception;
//
//    void updateAdvert(LiveAdvertModel advertModel) throws Exception;
//
//    void deleteAdvert(Map<String, Object> paramMap) throws Exception;

}
