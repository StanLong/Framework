package com.stanlong;

/**
 * 环形队列
 * 特点：类似手表上的表盘。
 *
 * 环形数组模拟队列思路：
 * 以手表的表盘为例， [0，1，2，3，4，5，6，7，8，9，10，11]
 *
 * 留出一个空闲位表示队满和队空
 *
 * 第一大类：定义： front 指向队首元素的前一个位置, rear 指向队尾元素
 * front = 0， rear = 0。 表示空队列
 *
 * 第一个元素入队保存在 rear + 1 的位置, front 的位置不保存元素。当第11个元素入队时 rear 指向11，和 front 只差一个位置时队满。
 * 此时队满的条件是 (rear + 1) % maxSize = front
 * 对应到表盘上就是 (11+1)%12=0 正好时 front 的指向位置
 *
 * 元素个数的计算:
 * 第一种情况： rear > front
 * 队列元素个数 = rear - front
 * 第二种情况： rear < front
 * 比如此时 front 指向 4， rear 指向 2 ， 则3，4位置上没有元素，元素个数为 10
 * 那元素个数就是 maxSize-front+rear = 12-4+2 = 10
 * 对以上两种情况进行总结得
 * 元素个数 = (rear-front+maxSize)%maxSize
 *
 * 第二大类：定义 rear 指向队尾元素的后一个位置， front指向对头元素
 * 其特性与第一大类一样
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {

    }


}
