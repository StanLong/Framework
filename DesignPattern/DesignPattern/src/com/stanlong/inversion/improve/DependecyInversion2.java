package com.stanlong.inversion.improve;

/**
 * ����������תԭ�������ӿ�
 * @author Administrator
 *
 */
public class DependecyInversion2 {

	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
		person.receive(new WeChat());
	}
}

// ����ӿ�
interface IRecever{
	String getInfo();
}


class Email implements IRecever{
	public String getInfo(){
		return "�����ʼ���Ϣ�� hello,world";
	}
}

class WeChat implements IRecever{
	public String getInfo(){
		return "΢����Ϣ�� hello,world";
	}
}

class Person{
	// �����ǶԽӿڵ�����
	public void receive(IRecever receiver){
		System.out.println(receiver.getInfo());
	}
}
