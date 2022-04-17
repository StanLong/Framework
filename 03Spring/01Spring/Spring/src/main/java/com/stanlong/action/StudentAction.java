package com.stanlong.action;

import com.stanlong.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("studentActionId")
// @Scope("prototype")
public class StudentAction {


	private StudentService studentService;

	@Autowired
	private void setStudentService(StudentService studentService){
		this.studentService = studentService;
	}

	public void execute(){
		studentService.addStudent();
	}
}
