package com.stanlong;

/**
 * 对象模式适配器
 */
public class DesignPattern {
    public static void main(String[] args) {
        System.out.println("对象适配器模式测试：");
        Adapter adapter = new Adapter();
        Target target = new ObjectAdapter(adapter);
        target.request();
    }
}

//目标接口
interface Target{
    public void request();
}
//适配者接口
class Adapter{
    public void specificRequest()    {
        System.out.println("适配者中的业务代码被调用！");
    }
}

//对象适配器类
class ObjectAdapter implements Target{
    private Adapter adapter;
    public ObjectAdapter(Adapter adapter)    {
        this.adapter=adapter;
    }
    public void request()    {
        adapter.specificRequest();
    }
}