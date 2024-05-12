package org.example.springboot.controller;

import org.apache.ibatis.annotations.Delete;
import org.example.springboot.common.Result;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.insertUser(user);
        return Result.success();
    }


    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteUser(id);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        userService.selectAll();
        return Result.success(userService.selectAll());
    }




}
