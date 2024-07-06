package org.example.springboot.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.entity.User;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.example.springboot.utils.TokenUtils.genToken;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    @Resource
    UserMapper userMapper;

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return this.getOne(queryWrapper);

    }


    public User login(User user) {
        User dbUser=selectByUsername(user.getUsername());
        if(dbUser==null){
            throw new ServiceException("用户不存在");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("密码错误");
        }
        String token =genToken(dbUser.getId().toString(),dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser=selectByUsername(user.getUsername());
        if(dbUser!=null){
            throw new ServiceException("用户名已存在");
        }
        userMapper.insert(user);
        return user;
    }

    public  void resetPassword(User user){
        User dbUser=selectByUsername(user.getUsername());
        if(dbUser==null){
            throw new ServiceException("用户不存在");
        }
        dbUser.setPassword("123456");
        userMapper.updateById(dbUser);
    }
}
