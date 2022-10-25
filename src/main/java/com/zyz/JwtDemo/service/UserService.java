package com.zyz.JwtDemo.service;

import com.zyz.JwtDemo.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    public UserDTO getUserByName(String userName) {
        UserDTO userDTO = new UserDTO();
        // 获取用户信息

        return userDTO;
    }
}
