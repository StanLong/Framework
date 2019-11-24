package com.stanlong.ocp.improve;

/**
 * ����ԭ��
 * ��������ʱ��ԭ����Ķ���С
 * @author Administrator
 *
 */
public class Ocp2 {
	public static void main(String[] args) {
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawShape(new Rectangle());
		graphicEditor.drawShape(new Circle());
	}
}

//����һ�����ڻ�ͼ����
class GraphicEditor{
	public void drawShape(Shape s){
		s.draw();
	}
}


// ����Ϊ������
abstract class Shape{
	int my_type;
	public abstract void draw();
}

class Rectangle extends Shape{
	Rectangle(){
		super.my_type = 1;
	}

	@Override
	public void draw() {
		System.out.println("���ƾ���");
		
	}
}

class Circle extends Shape{
	Circle(){
		super.my_type = 2;
	}
	@Override
	public void draw() {
		System.out.println("����Բ��");
		
	}
}