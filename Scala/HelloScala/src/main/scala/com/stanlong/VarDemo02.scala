package com.stanlong

/**
  * @author 矢量
  * @date 2020/7/7-21:47
  */
object VarDemo02 {

  def main(args: Array[String]): Unit = {
    var num = 10;
    println(num.isInstanceOf[Int]) // true

    var age = 10
    age = 30 //ok

    val num2 = 30
    //num2 = 40 // val 修饰的变量是不可改变的 val 没有线程安全问题，因此效率高，推荐使用
  }
}

// 在声明变量时，类型可以省略
// 类型在确定后就不能修改，scala是强数据类型语言
// 在声明/定义一个变量是，可以使用 var 后者 val 来修饰。 var 修饰的变量可改变，val 修饰的变量不可改变
// val 修饰的变量在编译后，等同于加上 final
