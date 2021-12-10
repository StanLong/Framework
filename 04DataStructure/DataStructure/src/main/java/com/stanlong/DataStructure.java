package com.stanlong;

/**
 * 单链表
 * 特点：
 * 由散落在内存上的各个节点连接组成
 * 每个节点包含数据域和指针域
 * 头节点的数据域不存放具体的数据，指针域指向下个节点的内存地址。
 * 可理解头节点是个引子，引出其后的节点。遍历单链表的时候，头节点不会被打印出来
 * 尾节点的指针域指向null
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        SingleLinkedList sll = new SingleLinkedList();
        Node node01 = new Node(4);
        Node node02 = new Node(5);
        Node node03 = new Node(1);
        Node node04 = new Node(2);

        // sll.add(node01);
        // sll.add(node02);
        // sll.add(node03);
        // sll.add(node04);

        sll.insert(node01);
        sll.insert(node02);
        sll.insert(node03);
        sll.insert(node04);


        sll.show();
    }
}

// 模拟内存中的节点
class Node{
    public int data; // 数据域
    public Node next; // 指针域（指向下一个节点）

    public Node(int data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node[" + "data=" + data + "]";
    }
}

// 定义单链表
class SingleLinkedList{
    // 初始化头节点
    private Node headNode = new Node(0); // 头节点不存放具体的数据

    // 添加(在尾部追加)
    public void add(Node node){
        // head 节点不能动，因此先定义一个辅助节点帮助遍历
        Node temp = headNode;

        // 1. 找到当前链表的最后节点
        while (true){
            if(temp.next == null){ // 指针域指向 null， 说明链表到最后了
                break;
            }
            // 如果没有到最后, 将 temp 后移
            temp = temp.next;
        }
        // 2. 将最后节点的指针域 next 指向新节点
        temp.next = node;
    }

    // 插入
    public void insert(Node node){
        Node temp = headNode;
        // 遍历单链表，查询插入位置
        while (true){
            if(temp.next == null){ // 如果链表是空的，退出循环
                break;
            }

            if(temp.next.data > temp.data){ // 找到可以插入的位置，退出循环
                break;
            }
            // 以节点数据与 1 2 3 4 为例
            // temp.next.data > temp.data
            // 进入链表的顺序为 head, head->1, head->2->1, head->3->2->1, head->4->3->2->1
            // 即最先插入的排在链表末尾，最后插入的排在链表头部， 遍历的时候，后进先出(栈)

            // temp.next.data < temp.data
            // 进入链表的顺序为 head, head->1, head->1->2, head->1->2->3,head->1->2->3->4,
            // 即最先插入的排在链表头部，最后插入的排在链表末尾， 遍历的时候，先入先出（队列）

            temp = temp.next; // 后移，遍历当前列表
        }
        node.next = temp.next;
        temp.next = node;
    }

    // 查看
    public void show(){
        // 判空
        if(headNode.next == null){
            System.out.println("链表为空");
            return;
        }

        // head 节点不能动，因此先定义一个辅助节点帮助遍历
        Node temp = headNode.next;
        while (true){
            if(temp==null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    // 修改
    // 删除
}