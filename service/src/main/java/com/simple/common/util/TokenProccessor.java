package com.simple.common.util;

import java.util.UUID;

/**
 * 生成唯一token
 * @author hejinguo
 * @version $Id: TokenProccessor.java, v 0.1 2019年11月19日 下午4:36:29
 */
public class TokenProccessor {

    private static class TokenProccessorHolder {
        private static TokenProccessor INSTACE = new TokenProccessor();
    }

    public static TokenProccessor getInstance() {
        return TokenProccessor.TokenProccessorHolder.INSTACE;
    }

    public String makeToken() { // checkException  
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(TokenProccessor.getInstance().makeToken());
    }
}
