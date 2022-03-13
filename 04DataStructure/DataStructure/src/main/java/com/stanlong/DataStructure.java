package com.stanlong;

/**
 * 动态规划算法
 * 背包问题
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        // 准备工作，声明变量
        int[] volume = {2, 3, 4, 5}; // 物品的体积
        int[] price = {3, 4, 5, 6}; // 物品的价值
        int capacity = 8; // 背包的容量
        int n = price.length; // 物品的个数

        // 创建一个二维数组
        // v[i][j] 表示在容量 i 的背包中能装入物品 j 的最大价值
        int[][] v = new int[capacity+1][n+1];

        // 初始化二维数组第一行和第一列
        for(int i=0; i<capacity; i++){
            v[0][capacity] = 0;
        }
        for(int i=0; i<n; i++){
            v[i][0] = 0;
        }

        //

    }
}