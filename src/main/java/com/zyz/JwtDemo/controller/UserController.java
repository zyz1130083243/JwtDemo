package com.zyz.JwtDemo.controller;

import com.zyz.JwtDemo.annotation.PassToken;
import com.zyz.JwtDemo.common.ApiResponse;
import com.zyz.JwtDemo.constants.UserConstant;
import com.zyz.JwtDemo.dto.UserDTO;
import com.zyz.JwtDemo.request.AccountLoginReq;
import com.zyz.JwtDemo.common.ApiRequest;
import com.zyz.JwtDemo.service.TokenService;
import com.zyz.JwtDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    /**
     * 账号登录
     * @param apiRequest
     * @return
     */
    @PostMapping("/accountLogin")
    public ApiResponse<String> accountLogin(@RequestBody ApiRequest<AccountLoginReq> apiRequest) {
        AccountLoginReq data = apiRequest.getData();
        UserDTO userDTO = userService.getUserByName(data.getUserName());
        //用户登录验证...

        //验证通过生成token
        String userId = "zyz";//临时写死
        String token = tokenService.buildToken(userId);

        return ApiResponse.ok(token);
    }


    /**
     * 退出登录
     * @param apiRequest
     * @return
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody ApiRequest<Void> apiRequest, HttpServletRequest request) {
        String userId = (String) request.getAttribute(UserConstant.USER_ID);
        tokenService.clearToken(userId);
        return ApiResponse.ok("登出成功！");
    }


    @GetMapping("/test")
    @PassToken
    public String test() {
        return "PassToken测试";
    }
}
