package com.stanlong;

/**
 * 接口适配器
 */
public class DesignPattern {
    public static void main(String[] args) {
        AbsAdapter absAdapter = new AbsAdapter() {
            @Override
            public void operation1() {
                System.out.println("使得匿名内部类实现 operation1");
            }

            @Override
            public void operation2() {
                System.out.println("使得匿名内部类实现 operation2");
            }
        };
        absAdapter.operation1();
        absAdapter.operation2();
        absAdapter.operation3();
        absAdapter.operation4();
    }
}

interface Interface{
    public void operation1();
    public void operation2();
    public void operation3();
    public void operation4();
}

abstract class AbsAdapter implements Interface{
    @Override
    public void operation1(){
        System.out.println("默认实现方法1");
    }
    @Override
    public void operation2(){
        System.out.println("默认实现方法2");
    }
    @Override
    public void operation3(){
        System.out.println("默认实现方法3");
    }
    @Override
    public void operation4(){
        System.out.println("默认实现方法4");
    }
}