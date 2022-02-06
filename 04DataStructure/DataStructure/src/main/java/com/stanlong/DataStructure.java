package com.stanlong;

import lombok.*;

/**
 * 删除二叉树节点
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        // 创建一颗二叉树
        BinaryTree bt = new BinaryTree();

        // 创建需要的节点
        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        // 把节点挂到树上
        bt.setRoot(root); // 设置树的根节点
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        node3.setRight(node7);

        bt.preOrder(); // 前序遍历
        System.out.println("--------------删除分割线--------------");
        bt.delNode(100); // 删除节点7
        bt.preOrder(); // 前序遍历
    }
}

@Getter
@Setter
@ToString(exclude = {"left", "right"})
@RequiredArgsConstructor
class Node{
    @NonNull
    private int id; // 数据域
    private Node left; // 左指针
    private Node right;  // 右指针

    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }

    // 删除节点
    public void delNode(int id){
        if(this.left != null && this.left.getId() == id){
            this.left = null;
            return;
        }
        if(this.right != null && this.right.getId() == id){
            this.right = null;
            return;
        }
        if(this.left != null){
            this.left.delNode(id);
        }
        if(this.right != null){
            this.right.delNode(id);
        }
    }
}

// 创建一个二叉树
class BinaryTree{
    @Setter
    private Node root; // 声明二叉树的根节点

    // 前序遍历
    public void preOrder(){
        if(this.root != null){
            this.root.preOrder();
        }else {
            System.out.println("空树，没有节点");
        }
    }

    // 删除节点
    public void delNode(int id){
        if(root != null){
            if(root.getId() == id){
                root = null;
            }else {
                root.delNode(id);
            }
        }else{
            System.out.println("空树，不能删除");
        }
    }
}
