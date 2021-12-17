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

        sll.add(node01);
        sll.add(node02);
        sll.add(node03);
        sll.add(node04);
        sll.show();

        System.out.println("=================修改==================");
        sll.update(node04);
        sll.show();

        // System.out.println("=================删除==================");
        // sll.delete(node01);
        // sll.show();
        // System.out.println("-----------------");
        // sll.delete(node04);
        // sll.show();

        // int length = sll.getLength();
        // System.out.println("链表的长度为: " + length);

        // int left = 4;
        // Node leftNode = sll.getLeft(left);
        // System.out.printf("链表第 %s 个节点是 %s ", left, leftNode);
        // System.out.println();

        // int right = 2;
        // Node rightNode = sll.getRight(right);
        // System.out.printf("链表倒数第 %s 个节点是 %s ", right, rightNode);


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

            if(temp.next.data < temp.data){ // 找到可以插入的位置，退出循环
                break;
            }

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
    public void update(Node node){
        if(headNode.next == null){
            System.out.println("链表是空的");
        }
        Node temp = headNode.next;
        while (true){
            if (temp == null){ // 尾节点如果是空的，则退出
                System.out.println("链表遍历结束，没有找到匹配的节点");
                break;
            }
            if(temp.data == node.data){ // 找到了要更改的节点
                temp.data = 100;
                break;
            }
            temp = temp.next;
        }
    }

    // 删除
    public void delete(Node node) {
        Node temp = headNode;
        while (true) {
            if (temp.next == null) { // 尾节点如果是空的，则退出
                System.out.println("链表遍历结束，没有找到要删除的节点");
                break;
            }
            if (temp.next.data == node.data) { // 找的了要删除的节点
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;

        }
    }

    // 求链表的长度
    public int getLength(){
        Node temp = headNode;
        int length = 0;
        if(temp.next == null){
            return length;
        }
        while (true){
            if(temp.next == null){
                break;
            }
            length = length + 1;
            temp = temp.next;
        }
        return length;
    }

    // 求链表左边第k给元素的值
    public Node getLeft(int index){
        int length = getLength();
        if(index > length || index < 0){
            System.out.println("输入的数字以越界");
            return null;
        }
        Node temp = headNode;
        if (temp.next == null){
            System.out.println("链表是空的");
            return null;
        }
        int len = 0;
        while (len != index){
            if(temp.next == null){
                break;
            }
            len = len +1;
            temp = temp.next;
        }
        return temp;
    }

    // 求链表右边第k个元素的值
    public Node getRight(int index){
        int length = getLength();
        if(index > length || index < 0){
            System.out.println("输入的数字以越界");
            return null;
        }
        Node temp = headNode;
        if (temp.next == null){
            System.out.println("链表是空的");
            return null;
        }
        int len = 0;
        while ((getLength() - len) != (index-1)){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
            len = len + 1;
        }
        return temp;
    }

    // 链表反转
    // 1. 先定义一个节点 reverseHead
    // 2. 从头到尾遍历原来的链表，将其取出，并放在新的链表的reverseHead的最前端
    // 3. head.next = reverseHead.next
    public void reverse(){
        if (headNode.next == null || headNode.next.next == null){ // 链表为空，或者链表只有一个节点，则无需反转
            return;
        }
        Node curr = headNode.next;
        Node next = null;  // 遍历原链表时，是把节点一个一个取出来的。为了遍历不至于中断，定义next用来保存当前节点的下一个节点
        Node reverseHead = new Node(0);

        /**
         * 语言描述
         * 假设原链表是 head->1->2->3
         * 第一轮循环
         * curr = 1 != null 进入循环体
         * next = 2
         * 1 原来是指向 2 的，现在指向 null 1->null
         * reverseHead 原来是指向 null 的，现在指向 1
         * curr = 2
         *
         * 第一轮循环结束
         * reverseHead -> null 变成 reverseHead -> 1 -> null
         *
         * 第二轮循环
         * curr = 2 != null 进入循环体
         * next = 3
         * 2 原来是指向 3 的，现在指向 1
         * reverseHead 原先是指向 1 的，现在指向 2
         * curr = 3
         *
         * 第二轮循环结束
         * reverseHead -> 1 -> null 变成 reverseHead -> 2 -> 1 -> null
         */
        while (curr != null){
            next = curr.next; // 将当前节点的下一个节点保存到next里
            curr.next = reverseHead.next;  // 改变当前节点的指向，即把节点从原链表中摘下来
            reverseHead.next = curr; // 把摘下来的节点拼接到reverseHead后面
            curr = next; // 继续摘下一个节点
        }
        headNode.next = reverseHead.next;

    }

    // 链表合并
}