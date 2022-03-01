package com.stanlong;

import lombok.NonNull;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

/**
 * 平衡二叉树
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        // int[] arr = {45, 12, 53, 3, 37, 100, 24, 61, 90, 78};
        int[] arr = {45, 12, 53, 3, 37, 24};
        AVLTree avlTree = new AVLTree();
        for(int i=0; i<arr.length; i++){
            avlTree.add(new Node(arr[i]));
        }
        System.out.println("------中序遍历------");
        avlTree.infixOrder();

        // 树的高度
        System.out.println("树的高度: " + avlTree.getRoot().height());

        // 左子树的高度
        System.out.println("左子树的高度: " + avlTree.getRoot().leftHeight());

        // 右子树的高度
        System.out.println("右子树的高度: " + avlTree.getRoot().rightHeight());
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

        // 加入新的节点后，判断树是否平衡
        if(leftHeight() - rightHeight() >= 2){ // 左比右高， 进行右旋转
            rightRotate();
        }
        if(leftHeight() - rightHeight() <= -2) { // 右比左高，进行左旋转

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

    /**
     * 返回当前节点为根节点时树的高度
     * @return 树的高度
     */
    public int height(){
        return Math.max(left == null ? 0: left.height(), right == null?0:right.height()) + 1;
    }

    /**
     * 左子树的高度
     * @return 左子树的高度
     */
    public int leftHeight(){
        if(left == null){
            return 0;
        }
        return left.height();
    }

    /**
     * 右子树的高度
     * @return 右子树的高度
     */
    public int rightHeight(){
        if(right == null){
            return 0;
        }
        return right.height();
    }

    /**
     * 右旋转
     */
    public void rightRotate(){
        // 1. 创建一个新的节点，值等于当前节点的值
        Node newRight = new Node(value);

        // 2. 把新节点的右子树，设置为当前节点的右子树
        newRight.right = right;

        // 3. 把新节点的左子树设置为当前节点左子树的右子树
        newRight.left = left.right;

        // 4. 把当前节点的值换为左子节点的值
        value = left.value;

        // 5. 把当前节点的左子树设置为左子树的左子树
        left = left.left;

        // 6. 把当且节点的右子树设置为新节点
        right = newRight;

    }

    /**
     * 左旋转
     */
    public void leftRotate(){

    }

}

/**
 * 平衡二叉树
 */
class AVLTree{
    @Getter
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