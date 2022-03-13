package com.stanlong;

/**
 * 分治算法最佳实践
 * 汉诺塔问题
 * 步骤:
 * 1. 先把最上面的盘从A移到B. 移动过程中会使用到C A->B
 * 2. 把最下面的盘移动到C A-C
 * 3. 把B的所有盘移动到C， 移动过程会使用到A B->C
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        haniTower(3, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔问题
     * @param num 盘子的数目
     * @param a 起始盘
     * @param b 辅助盘
     * @param c 目的盘
     */
    public static void haniTower(int num, char a, char b, char c){
        if(num == 1){ // 如果只有一个盘子
            System.out.println("第1个盘从" + a + "->" + c);
        }else{
            // 如果有 num >= 2 的情况， 可以只看成两个盘。 第一个盘是最下面的盘， 第二个盘是上面的所有盘
            // 1. 先把最上面的盘从A移到B. 移动过程中会使用到C
            haniTower(num-1, a, c, b);
            // 2. 把最下面的盘移动到C
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            // 3. 把B的所有盘移动到C， 移动过程会使用到A
            haniTower(num-1, b, a, c);

        }
    }
}