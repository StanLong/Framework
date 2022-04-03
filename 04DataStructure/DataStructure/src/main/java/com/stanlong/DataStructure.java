package com.stanlong;

/**
 * 工厂设计模式
 */
public class DataStructure {

    public static void main(String[] args) {

    }
}

abstract class Pizza{
    protected String name;

    // 准备原材料
    // 不同的Pizza原料也不一样，这里设计成抽象方法
    public abstract  void prepare();

    public void bake(){
        System.out.println(name + "bake~");
    }

    public void cut(){
        System.out.println(name + "cut~");
    }

    public void box(){
        System.out.println(name + "box~");
    }

    public void setName(String name){
        this.name = name;
    }
}

class GreekPizza extends Pizza{
    @Override
    public void prepare(){

    }
}

class CheesePizza extends Pizza{
    @Override
    public void prepare(){

    }
}

class PepperPizza extends Pizza{
    @Override
    public void prepare(){

    }
}

class OrderPizza{
    public OrderPizza{
        Pizza pizza = null;
        String orderType;
        do{
            orderType = "greek";
            if(orderType.equals("greek")){
                pizza = new GreekPizza();
            }else if (orderType.equals()){

            }else if(orderType.equals()){

            }else {
                break;
            }
        }while (true);
    }
}