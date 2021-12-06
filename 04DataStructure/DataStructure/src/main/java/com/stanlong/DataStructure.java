package com.stanlong;

import java.util.Scanner;

/**
 * 单向队列
 * 特点：先进先出
 *
 * 数组模拟队列思路：
 * 1. maxSize 队列最大长度
 * 2. front 对列头指针
 * 3. rare 队列尾指针
 */
public class DataStructure {

    public static void main(String[] args) throws Exception{
        ArrQueue aq = new ArrQueue(3);
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        char key = ' ';
        while (loop){
            System.out.println("s(show)显示队列");
            System.out.println("a(add)添加数据到队列");
            System.out.println("h(head)查看队首元素");
            System.out.println("r(rear)查看队尾元素");
            System.out.println("g(get)取出队首元素");
            System.out.println("e(exit)退出");
            System.out.print("输入选择: ");
            key = scanner.next().charAt(0);

            switch (key){
                case 's':
                    try {
                        aq.showQueue();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    try{
                        System.out.print("输入入队元素: ");
                        int data = scanner.nextInt();
                        aq.addQueue(data);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int result = aq.headQueue();
                        System.out.println("队首元素: " + result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'r':
                    try{
                        int result = aq.rearQueue();
                        System.out.println("队尾元素: " + result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try{
                        int result = aq.getQueue();
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

class ArrQueue{
    private int maxSize;
    private int front;
    private int rare;
    private int arr[];

    public ArrQueue(int maxSize){
        this.maxSize = maxSize;
        front = -1;
        rare = -1;
        arr = new int[maxSize];
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return front == rare;
    }

    // 判断队列是否已满
    public boolean isFull(){
        return rare == arr.length -1;
    }

    // 返回对首元素
    public int headQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的， 没有头元素");
        }
        return arr[front+1];
    }

    // 返回队尾元素
    public int rearQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的， 没有尾元素");
        }
        return arr[rare];
    }

    // 新增队列元素
    public void addQueue(int data){
        if(isFull()){
            throw new RuntimeException("队列满了，无法添加元素");
        }
        rare = rare + 1;
        arr[rare] = data;
    }

    // 元素出队列
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的，没有可出队列的元素");
        }
        front = front + 1; // 数组没变，只是下标往后移了一个位置
        return arr[front];
    }

    // 遍历队列中的所有元素
    public void showQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列是空的");
        }
        for(int i=0; i<arr.length; i++){
            System.out.printf("a[%d] = %d\t", i, arr[i]);
        }
        System.out.println();
    }

}
