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

        // 删除叶子节点
//        System.out.println("--------删除叶子节点--------");
//        bst.delNode(3);
//        bst.delNode(24);
//        bst.delNode(78);
//        bst.infixOrder();

        // 删除的节点只有左子树或者只有右子树
//        System.out.println("----删除的节点只有左子树或者只有右子树----");
//        bst.delNode(37);
//        bst.delNode(61);
//        bst.infixOrder();

        // 删除的节点既有左子树，也有右子树
        System.out.println("----删除的节点既有左子树，也有右子树----");
        bst.delNode(12);
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

    // 查找节点
    public Node search(int value){
        if(value == this.value){ // 找到
            return this;
        }else if(value < this.value){ // 往左子树递归
            if(this.left != null){
                return this.left.search(value);
            }
        }else{ // 往右子树递归
            if(this.right != null){
                return this.right.search(value);
            }
        }
        return null;
    }

    // 查找父节点
    public Node searchParent(int value){
        if((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)){ // 当前节点就是要删除节点的父节点
            return this;
        }else { //
            if(value < this.value && this.left != null){ // 向左子树递归
                return this.left.searchParent(value);
            }else if(value >= this.value && this.right != null){ // 向右子树递归
                return this.right.searchParent(value);
            }
        }
        return null;
    }

}

/**
 * 二叉排序树
 */
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

    public Node search(int value){
        if(root == null){
            return null;
        }else {
            return root.search(value);
        }
    }

    public Node searchParent(int value){
        if(root == null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }

    public void delNode(int value){
        if(root == null){
            return;
        }else {
            Node target = search(value); // 查找要删除的节点
            if(target == null){
                return;
            }
            // 如果该二叉树只有一个节点，那删除之后就是一个空树了
            if(root.getLeft() == null && root.getRight() == null && root.getValue() == value){
                root = null;
                return;
            }

            // 查找该节点的父节点
            Node parent = searchParent(value);

            // 1. 如果要删除的节点是叶子节点, 则直接删除该节点
            if(target.getLeft() == null && target.getRight() == null){
                if(parent.getLeft() != null && parent.getLeft().getValue() == value){ // 要删除的节点是左子节点
                    parent.setLeft(null);
                }else if(parent.getRight() != null && parent.getRight().getValue() == value) { // 要删除的是右子节点
                    parent.setRight(null);
                }
            } else if(target.getLeft() != null && target.getRight() != null){// 3.被删除的节点既有左子树，也有右子树
                int minValue = delRightMin(target.getRight()); // 删除并返回右子树节点的最小值
                target.setValue(minValue);  // 用右子树的最小值替换要删除的节点
            }else { // 2. 被删除的节点只有左子树或者只有右子树，用其左子树或者右子树替换该节点
                // 要删除的节点只有左子树
                if(target.getLeft() != null){
                    if(parent.getLeft() != null && parent.getLeft().getValue() == value){ //要删除的节点是父节点左子节点
                        parent.setLeft(target.getLeft());
                    }else { // 要删除的节点是父节点的右子节点
                        parent.setRight(target.getLeft());
                    }
                }else { // 要删除的节点有右子树
                    if(parent.getRight() != null && parent.getRight().getValue() == value){ //要删除的节点是父节点左子节点
                        parent.setRight(target.getRight());
                    }else { // 要删除的节点是父节点的右子节点
                        parent.setLeft(target.getRight());
                    }

                }
            }
        }
    }

    /**
     * 返回右子树最小值的节点，并删除该节点
     * @param node 要删除节点的右子树的第一个节点
     * @return 以node为根节点的二叉排序树的右子树最小值
     */
    public int delRightMin(Node node){
        Node target = node;
        while(target.getLeft() != null){
            target = target.getLeft();
        }
        delNode(target.getValue());
        return target.getValue();
    }
}