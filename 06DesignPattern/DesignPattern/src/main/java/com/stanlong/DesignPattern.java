package com.stanlong;

/**
 * 访问者模式
 * 以系统软件升级为例
 */
public class DesignPattern {
    public static void main(String[] args) {
        Robot robot = new Robot();
        System.out.println("没升级前");
        robot.calc();

        // 升级包
        Visitor updatePack = new UpdateVisitor();
        System.out.println("升级后");
        robot.accept(updatePack);
        robot.calc();
    }
}

/**
 * 抽象访问者
 * 定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作 visit() ，
 * 该操作中的参数类型标识了被访问的具体元素
 */
interface Visitor {
    void visitCPU(CPU cpu);
    void visitHardDisk(HardDisk hardDisk);
}

/**
 * 具体访问者
 * 实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么
 */
class UpdateVisitor implements Visitor { // 软件升级包
    @Override
    public void visitCPU(CPU cpu) {
        cpu.command = "1+1=2";
    }

    @Override
    public void visitHardDisk(HardDisk hardDisk) {
        hardDisk.command = "存储： 1+1=2";
    }
}

/**
 * 抽象元素类
 * 声明一个包含接受操作 accept() 的接口，被接受的访问者对象作为 accept() 方法的参数。
 */
abstract class Hardware { // 硬件
    String command;

    public Hardware(String command) {
        this.command = command;
    }

    public void run(){
        System.out.println(command);
    }

    public abstract void accept(Visitor visitor); // 对外暴露接口
}

/**
 * 具体元素类 ： CPU
 * 实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visit(this) ，另外具体元素中可能还包含本身业务逻辑的相关操作。
 */
class CPU extends Hardware {
    public CPU(String command) {
        super(command);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitCPU(this);
    }
}

/**
 * 具体元素类
 * 磁盘
 */
class HardDisk extends Hardware {
    public HardDisk(String command) {
        super(command);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitHardDisk(this);
    }
}

/**
 * 对象结构角色
 * 是一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常由 List、Set、Map 等聚合类实现
 */
class Robot {
    private HardDisk hardDisk;
    private CPU cpu;

    public Robot(){
        hardDisk = new HardDisk("存储 1+1=1");
        cpu = new CPU("1+1=1");
    }

    public void  calc(){
        cpu.run();
        hardDisk.run();
    }

    public void accept(Visitor visitor){
        cpu.accept(visitor);
        hardDisk.accept(visitor);
    }
}