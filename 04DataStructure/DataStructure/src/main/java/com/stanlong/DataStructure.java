package com.stanlong;

import lombok.NonNull;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

/**
 * 二叉排序树
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        int[] arr = {45, 12, 53, 3, 37, 100, 24, 61, 90, 78};
        BinarySortTree bst = new BinarySortTree();
        // 循环添加节点到二叉排序树
        for(int i=0; i< arr.length; i++){
            bst.add(new Node(arr[i]));
        }
        // 中序遍历二叉排序树
        bst.infixOrder();
    }


}

// Node 节点
@Setter
@Getter
@ToString(of={"value"})
@RequiredArgsConstructor
class Node{
    @NonNull
    private int value;
    private Node left;
    private Node right;

    /**
     * 用递归的方式添加节点
     * @param node 要添加的节点
     */
    public void add(Node node){
        if(node == null){
            return;
        }
        // 左子节点都比根节点小
        if(node.value < this.value){
            if(this.left == null){
                this.left = node;
            }else {
                this.left.add(node); // 递归往左子树添加
            }
        }
        // 右子节点都比根节点大, 如果值相同，将该子节点放在左右均可
        if(node.value >= this.value){
            if(this.right == null){
                this.right = node;
            }else {
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder(){
        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.infixOrder();
        }
    }
}

class BinarySortTree{
    private Node root;

    public void add(Node node){
        if(root == null){
            root = node;
        }else {
            root.add(node);
        }
    }

    public void infixOrder(){
        if(root != null){
            root.infixOrder();
        }else {
            System.out.println("树为空！");
        }
    }
}