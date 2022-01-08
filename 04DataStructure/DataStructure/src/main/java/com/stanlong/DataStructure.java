package com.stanlong;

/**
 * 八皇后问题
 */
public class DataStructure {

    // 八个皇后
    static int max=8;

    // 保存皇后放置位置的结果
    // 用一维数组模拟二维棋盘， 下标索引表示行，值表示列
    static int[] array = new int[max];

    // 统计有多少种解法
    static int count =0;

    public static void main(String[] args) throws Exception {
        check(0); // 开始在棋盘上放皇后
        System.out.printf("共有%d种解法", count);
    }

    // 放置皇后的方法
    private static void check(int n){ // n 表示皇后的个数
        if(n == max){ // 放置到最后一个
            print(); // 最后一个皇后放好后，打印所有结果
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for(int i=0; i<max; i++){ // 从 0 到 max -1 列
            // 把第 n 个皇后放到第 i 列
            array[n] = i;
            // 判断当放置第 n 个皇后到i列时，是否冲突
            if(judge(n)){ // 不冲突
                // 放第n+1个皇后
                check(n+1);
            }
            // 如果冲突，就继续执行 array[n] = i; 即将第n个皇后放置到后移的一个位置上

        }
    }

    // 查看当放置第n个皇后时，是否和已放置的冲突
    private static boolean judge(int n){
        for (int i=0; i<n; i++){
            // array[i] == array[n] 表示在同一列
            // Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示是否在同一斜线，
            //  这里用的是数学上求斜率的方法，因为棋盘是个正方形， 所以斜率的绝对值为1
            // n 传进来的值每次都是递增的，可以不用判断是否在同一行
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    // 输出八皇后问题的解法
    private static void print(){
        count = count + 1;
        for(int i=0; i< array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
