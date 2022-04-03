package com.stanlong;

import lombok.*;

public class DesignPattern {
    public static void main(String[] args) {
        System.out.println("----原型模式，浅拷贝-----");

        Sheep sheep = new Sheep("喜羊羊", 10);
        sheep.setFriend(new Sheep("美羊羊", 8));
        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();

        System.out.println("sheep = " + sheep + ": " + sheep.getFriend().hashCode());
        System.out.println("sheep1 = " + sheep1 + ": " + sheep1.getFriend().hashCode());
        System.out.println("sheep2 = " + sheep2 + ": " + sheep2.getFriend().hashCode());

        System.out.println("sheep == sheep1?: " + (sheep == sheep1));
        System.out.println("sheep == sheep2?: " + (sheep == sheep2));
        System.out.println("sheep1 == sheep2?: " + (sheep1 == sheep2));

        System.out.println(sheep.hashCode());
        System.out.println(sheep1.hashCode());
        System.out.println(sheep2.hashCode());
    }
}

@Getter
@Setter
@ToString(exclude = "friend")
@RequiredArgsConstructor
class Sheep implements Cloneable { // 原型类需要声明一个克隆自己的接口
    @NonNull
    private String name;
    @NonNull
    private int age;

    private Sheep friend; // 对象


    //克隆该实例，使用默认的clone方法来完成
    @Override
    protected Object clone()  {

        Sheep sheep = null;
        try {
            sheep = (Sheep)super.clone();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        // TODO Auto-generated method stub
        return sheep;
    }

}