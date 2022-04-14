package com.stanlong;


import java.util.Random;

/**
 * 职责链模式
 * 以请假审批流程为例
 */
public class DesignPattern {
    public static void main(String[] args) {
        // 声明处理流程的各节点
        PmLeaveApplyHandler pmLeaveApplyHandler = new PmLeaveApplyHandler();
        DmLeaveApplyHandler dmLeaveApplyHandler = new DmLeaveApplyHandler();
        GmLeaveApplyHandler gmLeaveApplyHandler = new GmLeaveApplyHandler();

        // 声明责任链
        pmLeaveApplyHandler.setHandler(dmLeaveApplyHandler);
        dmLeaveApplyHandler.setHandler(gmLeaveApplyHandler);

        Integer day = 10;
        System.out.println("张三请假：" + day + "天");
        pmLeaveApplyHandler.handlerApply(day);
    }
}

/**
 * 抽象处理者（Handler）角色：
 * 定义一个处理请求的接口，包含抽象处理方法和一个后继连接
 */
abstract class LeaveApplyHandler {
    protected Random random = new Random(10);
    // 定义下一个处理者的引用
    protected LeaveApplyHandler handler;

    // 这是下一个处理者
    public void setHandler(LeaveApplyHandler handler) {
        this.handler = handler;
    }

    // 处理逻辑方法
    public abstract void handlerApply(Integer day);
}

/**
 * 具体处理者（Concrete Handler）角色：
 * 实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者
 * 项目经理处理类
 */
class PmLeaveApplyHandler extends LeaveApplyHandler {
    @Override
    public void handlerApply(Integer day) {
        if (day <= 3) {
            String result = (random.nextInt(3)) % 2 == 0 ? "通过" : "不通过";
            System.out.println("项目经理审批，结果：" + result);
        } else {
            handler.handlerApply(day);
        }
    }
}

/**
 * 具体处理者（Concrete Handler）角色：
 * 实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者
 * 部门经理处理类：
 */
class DmLeaveApplyHandler extends LeaveApplyHandler {
    @Override
    public void handlerApply(Integer day) {
        if (day <= 5) {
            String result = (random.nextInt(5)) % 3 == 0 ? "通过" : "不通过";
            System.out.println("部门经理审批，结果：" + result);
        } else {
            handler.handlerApply(day);
        }
    }
}

/**
 * 具体处理者（Concrete Handler）角色：
 * 实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者
 * 总经理处理类：
 */
class GmLeaveApplyHandler extends LeaveApplyHandler {
    @Override
    public void handlerApply(Integer day) {
        String result = (random.nextInt(3)) % 2 == 0 ? "通过" : "不通过";
        System.out.println("项目经理审批，结果：" + result);
    }
}