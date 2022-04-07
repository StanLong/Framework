package com.stanlong;

/**
 * 外观模式
 * 可以类比现实生活中的万能遥控器
 */
public class DesignPattern {
    public static void main(String[] args) {
        Handset handset = new Handset();
        handset.method();
    }
}

/**
 * 外观角色
 * 万能遥控
 */
class Handset {
    private Television tv = new Television();
    private AirConditioning ac = new AirConditioning();
    public void method() {
        tv.open();
        tv.close();
        ac.open();
        ac.close();
    }
}

/**
 * 子系统角色
 * 电视机遥控器
 */
class Television {
    public void open() {
        System.out.println("开电视");
    }
    public void close() {
        System.out.println("关电视");
    }
}

/**
 * 子系统角色
 * 空调遥控器
 */
class AirConditioning {
    public void open() {
        System.out.println("开空调");
    }
    public void close() {
        System.out.println("关空调");
    }
}