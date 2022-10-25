package com.zyz.JwtDemo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiRequest<T> implements Serializable {

    /**
     * 请求ID
     */
    private String id;

    /**
     * 签名
     */
    private String sign;

    /**
     * 浏览器信息
     */
    private String userAgent;

    /**
     * 用户IP
     */
    private String remoteIp;

    /**
     * 请求时间戳
     */
    private Long timestamp;

    /**
     * 请求数据
     */
    private T data;

}
