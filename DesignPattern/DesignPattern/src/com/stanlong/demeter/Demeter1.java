package com.stanlong.demeter;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ط���
 * @author Administrator
 *
 */
public class Demeter1 {
	public static void main(String[] args) {
		SchoolManager schoolManager = new SchoolManager();
		// ���ѧԺԱ��id��ѧУ�ܲ�Ա��id
		schoolManager.printAllEmployee(new CollegeManager());
		
	}
}

// ѧУ�ܲ�Ա��
class Employee{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

// ѧԺ��Ա��
class CollegeEmployee{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

// ����ѧԺԱ��
class CollegeManager{
	public List<CollegeEmployee> getAllEmployee(){
		List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
		for(int i=0; i<10; i++){
			CollegeEmployee emp = new CollegeEmployee();
			emp.setId("ѧԺ��Ա��id=" + i);
			list.add(emp);
		}
		return list;
	}
}

// ѧУ�Ĺ�����
// ���� SchoolManager ��ֱ�������࣬   Employee�� CollegeManager��
// �� CollegeEmployee����ֱ�����ѣ���Ϊ�����Ծֲ���������ʽ���ֵ��࣬Υ���˵����ط���
// ֱ�����ѣ����ֳ�Ա������������������������ֵ�е���Ϊֱ������
class SchoolManager{
	public List<Employee> getAllEmployee(){
		List<Employee> list = new ArrayList<Employee>();
		for(int i=0; i<5; i++){
			Employee emp = new Employee();
			emp.setId("ѧԺ��Ա��id=" + i);
			list.add(emp);
		}
		return list;
	}
	
	// ���ѧУ�ܲ���ѧԺԱ����Ϣ
	void printAllEmployee(CollegeManager sub){
		// 
		List<CollegeEmployee> list1 = sub.getAllEmployee();
		System.out.println("---------------------�ֹ�˾Ա��-------------------------");
		for (CollegeEmployee e : list1) {
			System.out.println(e.getId());
		}
		
		List<Employee> list2 = this.getAllEmployee();
		System.out.println("---------------------ѧУ�ܲ�Ա��-------------------------");
		for (Employee e : list2) {
			System.out.println(e.getId());
		}
	}
}