package com.stanlong.inversion;

/**
 * ������������תԭ������������� 
 * @author Administrator
 *
 */
public class DependecyInversion {

	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
	}
}

class Email{
	public String getInfo() {
		return "�����ʼ���Ϣ�� hello,world";
	}
}

// ��� Person ������Ϣ�Ĺ���
// ��ʽһ���
// 1���򵥣��Ƚ������뵽
// 2��������ǻ�ȡ�Ķ�����΢�Ż��߶��ŵȵȣ�����Ҫ�����ࡣͬʱPersonҲҪ������Ӧ�ķ���
// 3�����˼·������һ�������IReceiver�� ��ʾ�����ߣ����� Person �� ��IRecever��������
//    ��ΪEmail�� wechat �ȵ����ڽ��յķ�Χ�����Ǹ���ʵ��IRecever�ӿھ�OK�������ͷ���������תԭ��
class Person{
	public void receive(Email email){
		System.out.println(email.getInfo());
	}
}


