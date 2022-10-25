package com.zyz.JwtDemo.constants;

public enum StateCode {

    OK(0, "请求成功"),

    SERVER_ERROR(10001, "网络异常"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(50001, "用户不存在"),

    /**
     * 无token
     */
    LOGIN_NOT_TOKEN(50002, "无token，请重新登录"),

    /**
     * 参数有误
     */
    LOGIN_PARAM_ERROR(50003, "参数有误"),

    /**
     * 无效token
     */
    TOKEN_INVALID(50004, "无效token")



    ;


    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StateCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
