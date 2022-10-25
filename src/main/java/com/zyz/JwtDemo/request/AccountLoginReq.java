package com.zyz.JwtDemo.request;

import lombok.Data;

@Data
public class AccountLoginReq {
    // 用户名
    private String userName;
    // 密码
    private String password;
}
