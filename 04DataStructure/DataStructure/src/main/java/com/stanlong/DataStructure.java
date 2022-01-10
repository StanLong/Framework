package com.stanlong;

import java.util.Arrays;

/**
 * 选择排序
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        int[] arr = {3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};

        for(int i=0; i<arr.length - 1; i++){
            // 假定最小值
            int minIndex = i;
            int min = arr[i];
            for(int j=i+1; j<arr.length; j++){
                if(min > arr[j]){ // 假定的min不是最小值
                    // 重制最小值
                    minIndex = j;
                    min = arr[j];
                }
            }
            if(minIndex != i){
                // 交换
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            System.out.println("第" + i + "轮选择排序结果: " + Arrays.toString(arr));
        }
        System.out.println("选择排序总结果: " + Arrays.toString(arr));
    }

}
