package com.bigevent.service;


import com.bigevent.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);


    // 注册
    void register(String username, String password);

    // 更新
    void update(User user);

    // 更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
