package org.example.springboot.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean isadministrator =false;
    private String token;
}
