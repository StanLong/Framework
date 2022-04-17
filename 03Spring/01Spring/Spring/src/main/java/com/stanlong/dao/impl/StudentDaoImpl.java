package com.stanlong.dao.impl;

import com.stanlong.dao.StudentDao;
import org.springframework.stereotype.Repository;

@Repository("studentDaoId")
public class StudentDaoImpl implements StudentDao {

	@Override
	public void addStudent() {
		System.out.println("l_web_annotation addStudent()");
	}

}
