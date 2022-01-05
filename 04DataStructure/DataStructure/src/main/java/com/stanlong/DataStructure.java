package com.stanlong;

/**
 * 阶乘
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        System.out.println(factorial(1));
        System.out.println(factorial(2));
        System.out.println(factorial(3));
        System.out.println(factorial(4));

    }

    public static int factorial(int data){
        if(data==1){
            return 1;
        }else {
            return factorial(data-1) * data;
        }
    }


}
