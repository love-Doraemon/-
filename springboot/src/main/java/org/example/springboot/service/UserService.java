package org.example.springboot.service;


import org.example.springboot.entity.User;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.springboot.utils.TokenUtils.genToken;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);

    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User login(User user) {
        User dbUser=userMapper.selectByUsername(user.getUsername());
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
        User dbUser=userMapper.selectByUsername(user.getUsername());
        if(dbUser!=null){
            throw new ServiceException("用户名已存在");
        }
        userMapper.insert(user);
        return user;
    }
}
