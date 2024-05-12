package org.example.springboot.mapper;


import org.apache.ibatis.annotations.*;
import org.example.springboot.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into `tb_user` (username,password,isadministrator) " +
            "values(#{username},#{password},#{isadministrator})")
    void insert(User user);

    @Update("update `tb_user` set username=#{username},password=#{password},isadministrator=#{isadministrator} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from `tb_user` where id=#{id}")
    void deleteUser(Integer id);

    @Select("select * from `tb_user`")
    List<User> selectAll();

    @Select("select * from `tb_user` where username=#{username}")
    User selectByUsername(String username);


    @Select("select * from `tb_user` where id=#{id}")
    User selectById(Integer integer);
}
