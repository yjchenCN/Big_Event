package com.bigevent.controller;

import com.bigevent.pojo.Result;
import com.bigevent.pojo.User;
import com.bigevent.service.UserService;
import com.bigevent.utils.JwtUtil;
import com.bigevent.utils.Md5Util;
import com.bigevent.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController //表明其为请求处理类
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired //要用service层的方法，所以要先注入service层的对象
    private UserService userService;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$",message = "用户名格式错误") String username, @Pattern(regexp = "^\\S{5,16}$",message = "密码格式错误") String password) {
        //查询用户，如果不存在再注册
        User u = userService.findByUsername(username);
        if(u != null)
        {
            return Result.error("用户已存在");
        }else {
            //注册用户
            userService.register(username,password);
            return Result.success();
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$",message = "用户名格式错误") String username, @Pattern(regexp = "^\\S{5,16}$",message = "密码格式错误") String password){
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        if (loginUser == null){
            return Result.error("用户不存在");
        }
        //校验密码
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map <String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");

    }

    //获取用户详细信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user =userService.findByUsername(username);
        return Result.success(user);
    }

    //更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }


    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL(message = "地址不合法") String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少参数");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!Md5Util.getMD5String(oldPwd).equals(loginUser.getPassword())){
            return Result.error("原密码不正确");
        }
        if (!newPwd.equals(rePwd)){
            return Result.error("两次密码不一致");
        }
        System.out.println(Md5Util.getMD5String(oldPwd));
        userService.updatePwd(newPwd);
        return Result.success();
    }

}


