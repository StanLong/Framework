package com.stanlong.spring;

public class UserServiceImpl implements UserService{
    @Override
    public void addUser() {
        System.out.println("ioc addUser");
    }
}
