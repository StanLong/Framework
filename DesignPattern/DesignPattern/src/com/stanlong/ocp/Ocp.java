package com.stanlong.ocp;

/**
 * ����ԭ��
 * ������
 * Υ�������ģʽ��ocpԭ�򣬼�����չ���ţ����޸Ĺرա��������Ǹ��������µĹ���ʱ���������޸Ĵ��롣���߾������޸Ĵ���
 * @author Administrator
 *
 */
public class Ocp {

	public static void main(String[] args) {
		GraphicEditor graphicEditor = new GraphicEditor();
		graphicEditor.drawRectangle(new Rectangle());
		graphicEditor.drawCircle(new Circle());
	}
	
}

// ����һ�����ڻ�ͼ����
class GraphicEditor{
	// ����Shape���󣬸��ݲ�ͬ��type�������Ʋ�ͬ��ͼ��
	public void drawShape(Shape s){
		if(s.my_type == 1){
			drawRectangle(s);
		}else if(s.my_type==2){
			drawCircle(s);
		}
	}
	
	public void drawRectangle(Shape r){
		System.out.println("����");
	}
	
	public void drawCircle(Shape c){
		System.out.println("Բ��");
	}
}

class Shape{
	int my_type;
}

class Rectangle extends Shape{
	Rectangle(){
		super.my_type = 1;
	}
}

class Circle extends Shape{
	Circle(){
		super.my_type = 2;
	}
}