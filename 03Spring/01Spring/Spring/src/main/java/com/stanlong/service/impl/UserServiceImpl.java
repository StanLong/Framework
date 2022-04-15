package com.stanlong.service.impl;

import com.stanlong.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("ioc addUser");
    }
}
