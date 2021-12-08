package com.stanlong;

import java.util.Scanner;

/**
 * 环形队列
 * 特点：类似手表上的表盘。
 *
 * 数组模拟环形队列思路：
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
 * 第一个元素入队后保存在 front=0 的位置。 rear 往后移一位（rear+1）
 * 队空：front = rear
 * 队满：(rear + 1) % maxSize = front
 * 元素格数：(rear-front+maxSize)%maxSize
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        CircleArray ca = new CircleArray(4);
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        char key = ' ';
        while (loop){
            System.out.println("s(show)显示队列");
            System.out.println("a(add)添加数据到队列");
            System.out.println("h(head)查看队首元素");
            System.out.println("g(get)取出队首元素");
            System.out.println("e(exit)退出");
            System.out.print("输入选择: ");
            key = scanner.next().charAt(0);

            switch (key){
                case 's':
                    try {
                        ca.showQueue();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    try{
                        System.out.print("输入入队元素: ");
                        int data = scanner.nextInt();
                        ca.addQueue(data);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int result = ca.headQueue();
                        System.out.println("队首元素: " + result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try{
                        int result = ca.getQueue();
                        System.out.println("出队元素: " + result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

}

// 第二大类代码实现
class CircleArray{
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public CircleArray(int maxSize){
        this.maxSize = maxSize;
        front = 0;
        rear = 0;
        arr = new int[maxSize];
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return front == rear;
    }

    // 判断队列是否已满
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    // 返回对首元素
    public int headQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的， 没有头元素");
        }
        return arr[front];
    }

    // 新增队列元素
    public void addQueue(int data){
        if(isFull()){
            throw new RuntimeException("队列满了，无法添加元素");
        }
        arr[rear] = data;
        rear = (rear + 1) % maxSize; // 取模是为了避免数组越界
    }

    // 元素出队列
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的，没有可出队列的元素");
        }

        // 1. 先把front指向的值保存到一个临时变量
        // 2. 将front后移，取模
        // 3. 返回临时变量
        int value = arr[front];
        front = (front+1) % maxSize;
        return value;
    }

    // 遍历队列中的所有元素
    public void showQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的");
        }

        for(int i = front; i< front + ((rear-front+maxSize)%maxSize); i++){
            System.out.printf("arr[%d]=%d\n", i%maxSize, arr[i%maxSize]);
        }

    }
}