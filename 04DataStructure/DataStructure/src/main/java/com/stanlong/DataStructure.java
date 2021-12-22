package com.stanlong;

/**
 * 约瑟夫环
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        CircleSingleLinkedList csld = new CircleSingleLinkedList();
        csld.add(5);
        csld.show();
        csld.out(1,2,5);

    }
}

// 模拟内存中的节点
class Node{
    private int data; // 数据域
    private Node next; // 指针域（指向下一个节点）


    public Node(int data){
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
/**
 * 构建一个单向环形链表的思路
 * 1. 先创建第一个节点，让first指向该节点，并形成环形
 * 2. 后面每创建一个新的节点，就把该节点加入到以有的环形链表中即可
 *
 * 遍历环形链表的思路
 * 1. 先定义一个辅助指针 temp，指向first节点
 * 2. 然后通过while循环遍历该环形链表，当 temp.next == first 时，遍历结束。
 */

class CircleSingleLinkedList{
    // 创建头节点，作为引入环形链表的引子
    Node headNode = null;

    /**
     * 新增
     * @param nums 加入的节点个数
     */
    public void add(int nums){ // nums 表示加入节点的数量
        if(nums < 1){
            System.out.println("节点个数不能少于1");
        }
        Node temp = null; // 辅助指针，用来帮助构建环形链表
        for(int i = 1; i<=nums; i++){
            Node node = new Node(i);
            if(i==1){ // 第一个节点自己构成环
                headNode = node;
                headNode.setNext(headNode);
                temp = headNode;
            }else{
                temp.setNext(node);
                node.setNext(headNode);
                temp = node;
            }
        }
    }

    // 展示
    public void show(){
        if(headNode==null){
            System.out.println("环形链表是空的");
            return;
        }
        Node temp = headNode;
        System.out.println("入环顺序：");
        while (true){
            System.out.printf("%d -> ", temp.getData());
            if(temp.getNext() == headNode){
                break;
            }
            temp = temp.getNext();
        }
        System.out.println();
    }

    /**
     * 出环形链表
     * @param startNo 从第几个开始数
     * @param countNum 数几下
     * @param nums 链表中节点的个数
     */
    public void out(int startNo, int countNum, int nums){
        if(headNode == null || startNo < 1 || startNo > nums){
            System.out.println("非法输入");
            return;
        }
        Node temp = headNode; // 辅助遍历指针

        // temp 指针紧跟在 headNode 后面
        while (true){
            if(temp.getNext() == headNode){
                break;
            }
            temp = temp.getNext();
        }

        // 将 temp 和 headNode 移动到 startNo 个位置。
        // 注意从 temp 和 headNode 开始数，因此移动的次数时 startNo-1
        for(int i=0; i < startNo-1; i++){
            headNode = headNode.getNext();
            temp = temp.getNext();
        }

        // 移动到 startNo 这个位置后，就可以准备将节点出环形队列了
        System.out.println("出环顺序：");
        while (true){
            if(temp == headNode){ // 只剩一个节点了
                break;
            }
            for(int j=0; j<countNum-1; j++){
                headNode = headNode.getNext();
                temp = temp.getNext();
            }
            System.out.printf("%d -> ", headNode.getData());
            headNode = headNode.getNext();
            temp.setNext(headNode);
        }
        System.out.println();
        System.out.printf("环中最后一个节点是 %d \n", headNode.getData());

    }
}