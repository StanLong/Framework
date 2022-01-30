package com.stanlong;

import java.util.Arrays;

/**
 * 基数排序
 * 对手机号进行排序
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        int[] array = {3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
        int[] result = radixSort(array);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 基数排序
     * @param array 待排序数组
     */
    public static int[] radixSort(int[] array){

        // 用于保存排序结果
        int[] result = new int[array.length];

        // 声明计数数组，长度最多10
        int[] count = new int[10];

        // 获取 array 中的最长数位值
        int maxLength = getLength(array);

        for(int i=0; i<maxLength; i++){
            int division = (int)Math.pow(10, i); // 返回 10 的 i 次方, 用于处理个位，十位，百位
            for(int j=0; j< array.length;j++){
                int num = array[j] / division % 10;
                // 解释下这一行 假设 array[j] = 123
                // division 为 1 时，123 / 1 % 10 = 3 个位数
                // division 为 10 时， 123 / 10 % 10 = 2 十位数
                // division 为 100 时， 123 / 100 % 10 = 1 百位数
                count[num]++;
            }
            // 对 count 数组进行变形
            for(int m=1; m< count.length; m++){
                count[m] = count[m]+count[m-1];
            }

            // 倒序遍历array， 开始排序
            for(int n=array.length-1; n>=0; n--){
                int num = array[n] / division % 10;
                count[num]--;
                result[count[num]] = array[n];
            }

            /**
             * arraycopy
             *  src：要复制的数组(源数组)
             *  srcPos：复制源数组的起始位置
             *  dest：目标数组
             *  destPos：目标数组的下标位置
             *  length：要复制的长度
             */
            // result 数组从0开始复制array.length个值到array数组中从0开始的位置
            // 这一步是保存每一轮的排序结果
            System.arraycopy(result, 0, array, 0, array.length);

            // 每一轮排序结束后，计数数组置0
            Arrays.fill(count, 0);
        }
        return result;
    }

    // 获取 array 中的最长数位值
    private static int getLength(int[] array){
        int max = Integer.MIN_VALUE;
        for(int data : array){
            int strLen = (data+"").length();
            max = Math.max(max, strLen);
        }
        return max;
    }
}