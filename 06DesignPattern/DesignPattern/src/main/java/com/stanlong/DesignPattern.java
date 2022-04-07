package com.stanlong;

import java.util.HashSet;
import java.util.Set;

/**
 * 享元模式
 * 运用共享技术有效地支持大量细粒度的对象
 * 以共享单车为例
 */
public class DesignPattern {
    public static void main(String[] args) {
        User user1 = new User("张三");
        User user2 = new User("李四");
        User user3 = new User("王五");

        Bike bike1 = BikeFactory.getInstance().getBike();
        bike1.ride(user1);
        bike1.back(user1);

        Bike bike2 = BikeFactory.getInstance().getBike();
        bike2.ride(user2);

        Bike bike3 = BikeFactory.getInstance().getBike();
        bike3.ride(user3);

        System.out.println(bike1==bike2);
        System.out.println(bike2==bike3);



    }
}

/**
 * 非享元角色
 * 用户
 */
class User {
    private String userName;
    User(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setInfo(String userName) {
        this.userName = userName;
    }
}

/**
 * 抽象享元角色
 * 共享单车
 */
abstract class Bike {
    protected Integer state = 0; // 0 未使用， 1 使用中

    // 不同的用户来使用这辆共享单车
    abstract void ride(User user);
    abstract void back(User user);

    public Integer getState(){
        return this.state;
    }

}

/**
 * 具体享元角色
 * 摩拜单车
 */
class MoBike extends Bike {
    private final String bikeId; // 摩拜单车自己的内部状态，定义了车的车架ID
    public MoBike(String bikeId){
        this.bikeId = bikeId;
    }

    @Override
    void ride(User user) {
        if(state == 1){
            System.out.println(bikeId + "车使用中");
            return;
        }
        state = 1;
        System.out.println(user.getUserName() + "骑" + bikeId + "车出行");
    }

    @Override
    void back(User user) {
        state = 0;
        System.out.println(user.getUserName() + "归还了" + bikeId + "车");
    }
}

/**
 * 享元工厂角色
 * 工厂生产共享单车
 */
class BikeFactory {
    // 饿汉式单例模式： 生产的每辆车都是一个对象
    private static final BikeFactory instance = new BikeFactory();
    public static BikeFactory getInstance(){
        return instance;
    }

    private final Set<Bike> pool = new HashSet<>();

    private BikeFactory(){
        for(int i=0; i<2; i++){ // 生产两辆摩拜单车
            pool.add(new MoBike(i + "号"));
        }
    }

    // 返回未使用的单车
    public Bike getBike(){
        for(Bike bike : pool){
            if(bike.getState() == 0){
                return bike;
            }
        }
        return null;
    }

}