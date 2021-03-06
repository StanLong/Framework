package com.stanlong

/**
  * @author 矢量
  * @date 2020/7/26-20:08
  * 函数式编程
  */
object MethondDemo {

  def main(args: Array[String]): Unit = {
    // 使用方法 先创建一个对象
    val dog = new Dog
    println(dog.sum(10,20))

    // 方法转函数
    val f1 = dog.sum _
    println("f1=" + f1)
    println("f1=" + f1(50,50))

    // 函数， 求两个数的和
    val f2 =(n1:Int, n2:Int) => {
      n1 + n2 // 函数体
    }
    println("f2=" + f2)
    println("f2=" + f2(5, 6))

    // 调用 getRes
    println("getRes=" + getRes(10, 20, '+'))
  }

  // 定义函数/方法
  def getRes(n1: Int, n2: Int, oper: Char)={
    if(oper == '+'){
      n1 + n2
    }else if(oper == '-'){
      n1 - n2
    }else{
      null
    }

  }
}

class Dog {
  def sum(n1:Int, n2:Int): Int = {
    n1+n2
  }
}