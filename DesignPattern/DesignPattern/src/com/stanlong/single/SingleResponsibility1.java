package com.stanlong.single;

/**
 * ��һְ��ԭ��
 * @author Administrator
 *
 */
public class SingleResponsibility1 {

	public static void main(String[] args) {
		Vehicle vehicle = new Vehicle();
		vehicle.run("Ħ�г�");
		vehicle.run("����");
		vehicle.run("�ɻ�");
	}
}

// ��ͨ������
// ��ʽһ
// 1���ڷ�ʽ1��run�����У�Υ���˵�һְ��ԭ��
// 2�� ����ķ�ʽ�ǳ��򵥣����ݽ�ͨ�������еķ�ʽ��ͬ���ֽ�ɲ�ͬ���༴��
class Vehicle{
	public void run(String vehicle){
		System.out.println(vehicle + "�ڹ�·������...");
	}
}
