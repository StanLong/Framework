package com.stanlong;

/**
 * 模板方法模式
 * 以到银行输业务为例
 */
public class DesignPattern  {
    public static void main(String[] args) {
        Business business1 = new User1();
        business1.templateMethod();

        Business business2 = new User2();
        business2.templateMethod();
    }
}


/**
 * 抽象类
 * 负责给出一个算法的轮廓和骨架
 * 它由一个模板方法和若干个基本方法构成
 */
abstract class Business {
    //模板方法
    public void templateMethod() {
        takeNumber(); // 取号
        lineUp(); // 排队
        doSomething(); // 办理业务
        if(hook()){
            customerService(); // 定制服务， 看情况实现
        }
        evaluate(); // 评分
    }
    //具体方法
    public void takeNumber() {
        System.out.println("取号");
    }

    //具体方法
    public void lineUp() {
        System.out.println("排队");
    }

    //具体方法
    public void evaluate(){
        System.out.println("给工作人员评分");
    }

    //抽象方法
    public abstract void doSomething();

    // 钩子方法
    public boolean hook(){
        return false;
    }

    public abstract void customerService();

}
//具体子类
class User1 extends Business {
    @Override
    public void doSomething() {
        System.out.println("张三办的是取钱业务");
    }

    @Override
    public void customerService() { // 子类视情况决定是否覆盖钩子方法

    }
}

//具体子类
class User2 extends Business {
    @Override
    public void doSomething() {
        System.out.println("李四办的是汇款业务");
    }

    @Override
    public void customerService() { // 子类视情况决定是否覆盖钩子方法

    }
}