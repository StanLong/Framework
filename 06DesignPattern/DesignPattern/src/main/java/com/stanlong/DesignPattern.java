package com.stanlong;

/**
 * 装饰器模式
 * 给只能拍照的相机加上美颜的滤镜
 */
public class DesignPattern {
    public static void main(String[] args) {
        Component p = new ConcreteComponent();
        p.operation();
        System.out.println("---------------------------------");
        Component d1 = new ConcreteDecorator1(p);
        d1.operation();
        System.out.println("---------------------------------");
        Component d2 = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
        d2.operation();
    }
}


//抽象构件角色
interface Component {
    public void operation();
}

//具体构件角色
class ConcreteComponent implements Component {
    public void operation() {
        System.out.println("拍照");
    }
}

//抽象装饰角色
abstract class Decorator implements Component {
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    public void operation() {
        component.operation();
    }
}

//具体装饰角色 - 给相机加个美颜效果
class ConcreteDecorator1 extends Decorator {
    public ConcreteDecorator1(Component component) {
        super(component);
    }

    public void operation() {
        super.operation();
        System.out.println("给能拍照的相机再添加一个美颜效果");
    }
}

//具体装饰角色 - 给相机加个美颜效果
class ConcreteDecorator2 extends Decorator {
    public ConcreteDecorator2(Component component) {
        super(component);
    }

    public void operation() {
        super.operation();
        System.out.println("给能拍照的相机再添加一个滤镜效果");
    }
}