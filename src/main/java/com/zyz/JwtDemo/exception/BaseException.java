package com.zyz.JwtDemo.exception;

import com.zyz.JwtDemo.constants.StateCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{

    private Integer code;

    private String msg;

    public BaseException(StateCode stateCode) {
        super(stateCode.getMsg());
        this.code = stateCode.getCode();
        this.msg = stateCode.getMsg();
    }
}
