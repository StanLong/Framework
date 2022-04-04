package com.stanlong;

import java.math.BigDecimal;

/**
 * 桥接模式-实现支付功能
 */
public class DesignPattern {
    public static void main(String[] args) {

        System.out.println("\r\n模拟测试场景；微信支付、人脸方式。");
        Pay wxPay = new WxPay(new PayFaceMode());
        wxPay.transfer("weixin_1092033111", "100000109893", new BigDecimal(100));

        System.out.println("\r\n模拟测试场景；支付宝支付、指纹方式。");
        Pay zfbPay = new ZfbPay(new PayFingerprintMode());
        zfbPay.transfer("jlu19dlxo111", "100000109894", new BigDecimal(100));
    }
}

/**
 * 抽象化角色
 * 定义抽象类，并包含一个对实现化对象的引用
 */
abstract class Pay {

    protected IPayMode payMode; // 包含一个对实现化对象的引用

    public Pay(IPayMode payMode) {
        this.payMode = payMode;
    }

    public abstract String transfer(String uId, String tradeId, BigDecimal amount);

}

/**
 * 扩展抽象化角色
 * 是抽象化角色的子类，实现父类中的业务方法。
 * 并通过组合关系调用实现化角色中的业务方法。
 */
class WxPay extends Pay {

    // 通过组合关系调用实现化角色中的业务方法。
    public WxPay(IPayMode payMode) {
        super(payMode);
    }

    // 实现父类中的业务方法
    public String transfer(String uId, String tradeId, BigDecimal amount) {
        System.out.printf("模拟微信渠道支付划账开始。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
        boolean security = payMode.security(uId);
        System.out.printf("模拟微信渠道支付风控校验。uId：{%s} tradeId：{%s} security：{%s}", uId, tradeId, security);
        if (!security) {
            System.out.printf("模拟微信渠道支付划账拦截。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
            return "0001";
        }
        System.out.printf("模拟微信渠道支付划账成功。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
        return "0000";
    }
}

/**
 * 扩展抽象化角色
 * 是抽象化角色的子类，实现父类中的业务方法。
 * 并通过组合关系调用实现化角色中的业务方法。
 */
class ZfbPay extends Pay {

    // 通过组合关系调用实现化角色中的业务方法。
    public ZfbPay(IPayMode payMode) {
        super(payMode);
    }

    // 实现父类中的业务方法
    public String transfer(String uId, String tradeId, BigDecimal amount) {
        System.out.printf("模拟支付宝渠道支付划账开始。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
        boolean security = payMode.security(uId);
        System.out.printf("模拟支付宝渠道支付风控校验。uId：{%s} tradeId：{%s} security：{%s}", uId, tradeId, security);
        if (!security) {
            System.out.printf("模拟支付宝渠道支付划账拦截。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
            return "0001";
        }
        System.out.printf("模拟支付宝渠道支付划账成功。uId：{%s} tradeId：{%s} amount：{%s}", uId, tradeId, amount);
        return "0000";
    }
}

/**
 * 实现化（Implementor）角色
 * 定义实现化角色的接口，供扩展抽象化角色调用。
 */
interface IPayMode {
    boolean security(String uId);
}

/**
 * 具体实现化（Concrete Implementor）角色
 * 给出实现化角色接口的具体实现。
 */
class PayCypher implements IPayMode{
    public boolean security(String uId) {
        System.out.println("密码支付，风控校验环境安全");
        return true;
    }
}

/**
 * 具体实现化（Concrete Implementor）角色
 * 给出实现化角色接口的具体实现。
 */
class PayFaceMode implements IPayMode{
    public boolean security(String uId) {
        System.out.println("人脸支付，风控校验脸部识别");
        return true;
    }
}

/**
 * 具体实现化（Concrete Implementor）角色
 * 给出实现化角色接口的具体实现。
 */
class PayFingerprintMode implements IPayMode{
    public boolean security(String uId) {
        System.out.println("指纹支付，风控校验指纹信息");
        return true;
    }
}