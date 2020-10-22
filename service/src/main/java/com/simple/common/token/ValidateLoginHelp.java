package com.simple.common.token;

import com.simple.common.api.BaseResponse;
import com.simple.common.api.ResultCode;
import com.simple.common.redis.JedisDBEnum;
import com.simple.common.redis.JedisHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户登录验证
 *
 * @author hejinguo
 * @version $Id: ValidateLoginHelp.java, v 0.1 2019年11月20日 下午1:57:02
 */
public class ValidateLoginHelp {
    private static final Logger logger = LoggerFactory.getLogger(ValidateLoginHelp.class);

    /**
     * 用户登录验证
     *
     * @param
     * @return
     */
    public static BaseResponse validateToken(String token ) {


        if (StringUtils.isBlank(token)) {
            logger.error("请求参数TokenId不能为空!");
            return BaseResponse.build().code(ResultCode.FAILURE).message("未登录，请登录");
        }
        if (!JwtUtils.verifyToken(token)){
            return BaseResponse.build().code(ResultCode.FAILURE).message("Token无效");
        }
        //step2:根据token获取userId
        String wechatTokenValue = JedisHelper.getInstance().get(token, JedisDBEnum.WECHAT);
        String tokenValue = JedisHelper.getInstance().get(token, JedisDBEnum.PC);

        if (StringUtils.isBlank(tokenValue) && StringUtils.isBlank(wechatTokenValue)) {
            logger.error("登录过期，请重新登录");
            return BaseResponse.build().code(ResultCode.FAILURE).message("登录过期，请重新登录");
        }
        if (StringUtils.isBlank(tokenValue)) {
            tokenValue = wechatTokenValue;
        }
        return BaseResponse.build();
    }


    public static boolean refreshToken(String token, int applicationType) {
        int expTimeSeconds = 60 * 60 * 24 * 3;
        if (StringUtils.isBlank(token)){
            return false;
        }

        String[] tokenArray = null;
        if (applicationType == 3) {//微信
            JedisHelper.getInstance().setExpireSeconds(token, expTimeSeconds, JedisDBEnum.WECHAT);
        } else if (applicationType == 4) {//pc
            JedisHelper.getInstance().setExpireSeconds(token, expTimeSeconds, JedisDBEnum.PC);
        } else {
            JedisHelper.getInstance().setExpireSeconds(token, expTimeSeconds, JedisDBEnum.PC);
        }
        return true;
    }

}
