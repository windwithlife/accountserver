package com.simple.common.token;

import com.simple.common.redis.JedisDBEnum;
import com.simple.common.redis.JedisHelper;
import org.apache.commons.lang.StringUtils;


public class UserTokenHelp {

    public static Integer getWechatUserId(String token) {
        if(StringUtils.isBlank(token)){
            return  null;
        }
        String tokenValue = JedisHelper.getInstance().get(token, JedisDBEnum.WECHAT);
        if(StringUtils.isBlank(tokenValue)){
            return  null;
        }
        String[] tokenValueAry= DesTokenUtil.decrypt(tokenValue).split(",");
        return  Integer.valueOf(tokenValueAry[0]);
    }

    /**
     * 获取PC用户信息
     * @param token
     * @return
     */
    public static Integer getPcUserId(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String tokenValue = JedisHelper.getInstance().get(token, JedisDBEnum.PC);
        if (StringUtils.isBlank(tokenValue)) {
            return null;
        }
        String[] tokenValueAry = DesPcTokenUtil.decrypt(tokenValue).split(",");
        return Integer.valueOf(tokenValueAry[0]);
    }

}
