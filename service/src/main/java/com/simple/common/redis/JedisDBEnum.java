package com.simple.common.redis;


public enum JedisDBEnum {
    PC(0), WECHAT(1);

    private int value;

    JedisDBEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
