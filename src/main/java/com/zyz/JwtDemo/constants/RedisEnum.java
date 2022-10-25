package com.zyz.JwtDemo.constants;

import java.text.MessageFormat;

public enum RedisEnum {

    // token
    TOKEN_VERSION("TOKEN_VERSION_{0}", TTLConstant.ONE_HOUR,1.0)
    ;

    private String key;

    private int expiredTime;

    private double version;

    public String getKey() {
        return key;
    }

    RedisEnum(String key, int expiredTime, double version) {
        this.key = key + "_" + version;
        this.expiredTime = expiredTime;
        this.version = version;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public String buildKey(String...keys) {
        return MessageFormat.format(this.getKey(), keys);
    }
}
