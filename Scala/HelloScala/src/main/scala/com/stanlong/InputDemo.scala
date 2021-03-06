package com.stanlong

import scala.io.StdIn

/**
  * @author 矢量
  * @date 2020/7/11-18:54
  */
object InputDemo {

  def main(args: Array[String]): Unit = {
    /*
     * 可以从控制台接收用户信息【姓名，年龄，薪水】
     */
    println("请输入姓名:")
    val name = StdIn.readLine()
    println("请输入年龄:")
    val age = StdIn.readInt()
    println("请输入薪资:")
    val sal = StdIn.readDouble()
    printf("用户的信息为 name=%s age = %d sal = %f", name, age, sal)
  }
}
