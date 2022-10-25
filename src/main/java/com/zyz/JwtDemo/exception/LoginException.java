package com.zyz.JwtDemo.exception;

import com.zyz.JwtDemo.constants.StateCode;

public class LoginException extends BaseException {
    public LoginException(StateCode stateCode) {
        super(stateCode);
    }
}
