package org.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import org.example.springboot.common.AuthAccess;
import org.example.springboot.common.Result;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class WebController {
    @Resource
    UserService userService;


    @AuthAccess
    @GetMapping("/")
    public Result hello()
    {
        return Result.success("success");
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
        return Result.error("用户名或密码不能为空");
    }
        user=userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.error("用户名或密码不能为空");
        }
        if(user.getUsername().length()>15||user.getPassword().length()>20){
            return Result.error("用户名长度不能超过15,密码不长于20");
        }
        user=userService.register(user);
        return Result.success(user);
    }
}
