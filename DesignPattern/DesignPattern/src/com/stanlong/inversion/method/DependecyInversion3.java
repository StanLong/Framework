package com.stanlong.inversion.method;

/**
 * ����������תԭ��������ϵ�Ĵ��ݷ�ʽ
 * @author Administrator
 *
 */
public class DependecyInversion3 {

	public static void main(String[] args) {
		// ��ʽһ����
		/*ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose();
		openAndClose.open(ch);*/
		
		// ��ʽ������
		/*ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose(ch);
		openAndClose.open();*/
		
		// ��ʽ������
		ChangeHong ch = new ChangeHong();
		OpenAndClose openAndClose = new OpenAndClose();
		openAndClose.setTV(ch);
		openAndClose.open();
	}
}

// ��ʽ1�� ͨ���ӿڴ���
/*interface IOpenAndClose{
	public void open(ITV tv);
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public void open(ITV tv){
		tv.play();
	}
}*/

// ��ʽ2�� ͨ�����췽����������
/*interface IOpenAndClose{
	public void open();
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public ITV tv;
	public OpenAndClose(ITV tv){
		this.tv = tv;
	}
	@Override
	public void open() {
		this.tv.play();
	}
}*/

// ��ʽ3�� ͨ��setter��������
interface IOpenAndClose{
	public void open();
	public void setTV(ITV tv);
}

interface ITV{
	public void play();
}

class OpenAndClose implements IOpenAndClose{
	public ITV tv;

	@Override
	public void setTV(ITV tv) {
		this.tv = tv;
	}
	
	@Override
	public void open() {
		this.tv.play();
	}
}

class ChangeHong implements ITV{

	@Override
	public void play() {
		System.out.println("������ӻ���");
	}
	
}

