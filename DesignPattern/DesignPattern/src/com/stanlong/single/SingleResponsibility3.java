package com.stanlong.single;

/**
 * ��һְ��ԭ��
 * @author Administrator
 *
 */
public class SingleResponsibility3 {

	public static void main(String[] args) {
		Vehicle2 vehicle = new Vehicle2();
		vehicle.run("����");
		vehicle.runAir("�ɻ�");
		vehicle.runWater("�ִ�");
	}
}

// ��ʽ3�ķ���
// 1�������޸ķ���û�ж�ԭ������������޸ģ�ֻ�����ӷ���
// 2��������Ȼû������ļ��������ص�һְ��ԭ�򣬵����ڷ�����������Ȼ���ص�һְ��ԭ��
class Vehicle2{
	public void run(String vehicle){
		System.out.println(vehicle + " �ڹ�·����...");
	}
	public void runAir(String vehicle){
		System.out.println(vehicle + " ���������...");
	}
	public void runWater(String vehicle){
		System.out.println(vehicle + " ��ˮ������...");
	}
}
