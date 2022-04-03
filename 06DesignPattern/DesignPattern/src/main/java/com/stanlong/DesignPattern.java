package com.stanlong;

import lombok.*;

public class DesignPattern {
    public static void main(String[] args) {
        System.out.println("----原型模式-----");

        Sheep sheep = new Sheep("喜羊羊", 10, "白色");
        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();
        Sheep sheep3 = (Sheep) sheep.clone();

        System.out.println("sheep1 = " + sheep1 );
        System.out.println("sheep2 = " + sheep2 );
        System.out.println("sheep3 = " + sheep3 );
    }
}

@Getter
@Setter
@ToString
@RequiredArgsConstructor
class Sheep implements Cloneable { // 原型类需要声明一个克隆自己的接口
    @NonNull
    private String name;
    @NonNull
    private int age;
    @NonNull
    private String color;

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