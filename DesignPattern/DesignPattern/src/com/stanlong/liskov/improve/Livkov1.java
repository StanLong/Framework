package com.stanlong.liskov.improve;

/**
 * �����滻ԭ��
 * @author Administrator
 *
 */
public class Livkov1 {

	public static void main(String[] args) {
		A a = new A();
		System.out.println("11-3=" + a.func1(11, 3));
		System.out.println("1-8=" + a.func1(1, 8));
		
		System.out.println("-----------------------");
		
		// ��ΪB�಻�ټ̳�A�࣬��˵����߲��ڻ���Ϊfunc1�������
		// ������ɵĹ��ܻ����ȷ
		B b = new B();
		System.out.println("11+3=" + b.func1(11, 3));    
		System.out.println("1+8=" + b.func1(8, 3));
		
		// ʹ�������Ȼ����ʹ�õ�A��ķ���
		System.out.println("11-3=" + b.func3(11, 8));
	}
}
// ����һ�����ӻ����ļ���
class Base{
	// �Ѹ��ӻ����ķ���д�� Base ��
	
}

class A extends Base{
	// �����������Ĳ�
	public int func1(int num1, int num2){
		return num1 - num2;
	}
}

class B extends Base{

	// ���B��Ҫʹ��A��ķ�����ʹ����Ϲ�ϵ
	private A a = new A();
	
	public int func1(int a, int b){
		return a+b;
	}
	
	public int func2(int a, int b){
		return func1(a, b) + 9;
	}
	
	// ������ʹ��A���еķ���
	public int func3(int a, int b){
		return this.a.func1(a, b);
	}
}