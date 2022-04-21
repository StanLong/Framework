package com.stanlong.service.impl;

import com.stanlong.service.UserService;
import org.springframework.stereotype.Service;

@Service("userServiceId")
public class UserServiceImpl implements UserService {

	@Override
	public String addUser() {
		System.out.println("s_annotation_aspectj addUser");
		return "StanLong";
	}

	@Override
	public void updateUser() {
		int i = 1/0;
		System.out.println("s_annotation_aspectj updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("s_annotation_aspectj deleteUser");
	}

}