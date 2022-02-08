package com.stanlong;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 顺序存储二叉树
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        int array[] = {1,2,3,4,5,6,7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        System.out.println("--------------前序遍历顺序存储二叉树--------------");
        arrayBinaryTree.preOrder();
        System.out.println("--------------中序遍历顺序存储二叉树--------------");
        arrayBinaryTree.infixOrder();
        System.out.println("--------------后序遍历顺序存储二叉树--------------");
        arrayBinaryTree.postOrder();

    }
}


// 创建一个二叉树
@RequiredArgsConstructor
class ArrayBinaryTree{
    @NonNull
    private int[] array; // 有参构造方法

    // 重载 preOrder， 在主方法中调用时就不需要传参了
    public void preOrder(){
        this.preOrder(0);
    }

    public void infixOrder(){
        this.infixOrder(0);
    }

    public void postOrder(){
        this.postOrder(0);
    }



    // 前序遍历二叉树顺序存储结构
    public void preOrder(int index){
        if(array == null || array.length == 0){
            System.out.println("数组是空的");
            return;
        }
        // 打印当前节点
        System.out.println(array[index]);

        // 迭代左边
        if(index*2 + 1 < array.length){
            preOrder(index*2 + 1);
        }

        // 迭代右边
        if(index*2+2 < array.length){
            preOrder(index*2+2);
        }
    }

    // 中序遍历二叉树顺序存储结构
    public void infixOrder(int index){
        // 迭代左边
        if(index*2 + 1 < array.length){
            infixOrder(index*2 + 1);
        }

        // 打印当前节点
        System.out.println(array[index]);

        // 迭代右边
        if(index*2+2 < array.length){
            infixOrder(index*2+2);
        }
    }

    // 后序遍历二叉树顺序存储结构
    public void postOrder(int index){
        // 迭代左边
        if(index*2 + 1 < array.length){
            postOrder(index*2 + 1);
        }

        // 迭代右边
        if(index*2+2 < array.length){
            postOrder(index*2+2);
        }

        // 打印当前节点
        System.out.println(array[index]);
    }




}
