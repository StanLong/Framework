package com.stanlong.service.impl;

import com.stanlong.dao.StudentDao;
import com.stanlong.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	
	@Autowired
	@Qualifier("studentDaoId")
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Override
	public void addStudent() {
		studentDao.addStudent();
	}

	@PostConstruct
	public void myInit(){
		System.out.println("初始化方法");
	}

	@PreDestroy
	public void myDestroy(){
		System.out.println("销毁方法");
	}

	
}
