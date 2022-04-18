package com.stanlong.service.impl;

import com.stanlong.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("p_spring_half_auto addUser");
	}

	@Override
	public void updateUser() {
		System.out.println("p_spring_half_auto updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("p_spring_half_auto deleteUser");
	}

}
