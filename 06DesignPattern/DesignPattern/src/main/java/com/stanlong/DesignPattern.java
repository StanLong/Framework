package com.stanlong;

import java.util.ArrayList;

/**
 * 组合模式
 * 可以按照三级菜单来理解
 * 比如：
 * 安徽新华学院
 *     电子通信工程学院
 *         通信工程
 *         测试技术与仪器
 *     信息工程学院
 *         软件工程
 *         网络工程
 */
public class DesignPattern {
    public static void main(String[] args) {
        // 节点准备
        Component c0 = new Composite("安徽新华学院");
        Component c1 = new Composite("电子通信工程学院");
        Component c2 = new Composite("信息工程学院");
        Component leaf1 = new Leaf("通信工程");
        Component leaf2 = new Leaf("测试技术与仪器");
        Component leaf3 = new Leaf("软件工程");
        Component leaf4 = new Leaf("网络工程");

        // 开始组合
        c0.add(c1);
        c0.add(c2);
        c1.add(leaf1);
        c1.add(leaf2);
        c2.add(leaf3);
        c2.add(leaf4);
        c0.operation();
    }
}

/**
 * 抽象构件角色
 * 主要作用是为树叶构件和树枝构件声明公共接口
 */
abstract class Component {
    private String name;
    public String getName(){
        return this.name;
    }
    public Component(String name){
        this.name = name;
    }
    public void add(Component c){};
    public void remove(Component c){};
    public Component getChild(int i){return null;};
    public void operation(){};
}

/**
 * 树枝构件
 * 是组合中的分支节点对象，它有子节点，用于继承和实现抽象构件
 */
class Composite extends Component {
    private String name;
    public Composite(String name){
        super(name);
    }
    private ArrayList<Component> children = new ArrayList<Component>();
    public void add(Component c) {
        children.add(c);
    }
    public void remove(Component c) {
        children.remove(c);
    }
    public Component getChild(int i) {
        return children.get(i);
    }
    public void operation() {
        for (Object obj : children) {
            ((Component) obj).operation();
        }
    }
}

/**
 * 树叶构件
 * 是组合中的叶节点对象，它没有子节点，用于继承或实现抽象构件
 */
class Leaf extends Component {
    public Leaf(String name){
        super(name);
    }
    public void add(Component c) {
    }
    public void remove(Component c) {
    }
    public Component getChild(int i) {
        return null;
    }
    public void operation() {
        System.out.println("节点：" + getName() + "：被访问！");
    }
}