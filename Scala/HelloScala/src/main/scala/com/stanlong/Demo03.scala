package com.stanlong

/**
  * @author 矢量
  * @date 2020/7/11-18:08
  */
object Demo03 {

  def main(args: Array[String]): Unit = {
    var r1 : Int = 10/3
    println("r1=" + r1) //3

    var r2 : Double = 10/3
    println("r2=" + r2) // 3.0

    var r3 : Double = 10.0/3
    println("r3=" + r3) // 3.333333
    println("r3=" + r3.formatted("%.2f")) // 3.33

    // 取模的运算原则 a % b = a - a/b * b
    println(10 % 3) //1
    println(-10 % 3) // -1
    println(10 % -3) // -1
    println(-10 % -3) // -10 % -3 = -10 -(-10 / -3) * (-3) = -10 -3*(-3) = -10+9 = -1

    // scala 中没有 ++ 和 --， 而使用 +=1 和 -=1
    var num1=10
    // num1++ 错误
    num1 += 1

    //num1-- 错误
    num1 -= 1
    println(num1) //10

  }
}
