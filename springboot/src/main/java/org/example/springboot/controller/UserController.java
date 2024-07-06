package org.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.example.springboot.common.Result;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }


    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList =userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(userList);
    }
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user =userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username){
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
            queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
            Page<User> page= userService.page(new Page<>(pageNum,pageSize),queryWrapper);
            return Result.success(page);

    }

}
