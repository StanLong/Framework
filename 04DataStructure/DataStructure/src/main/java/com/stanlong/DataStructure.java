package com.stanlong;

import java.util.Arrays;

/**
 * 斐波那契查找
 * 需要借助斐波那契数列查找
 */
public class DataStructure {

    // 斐波那契数列的长度
    public static int maxSize = 20;

    public static void main(String[] args) throws Exception {
        int[] array = {2, 3, 4, 5, 15, 19, 26, 27, 36, 38, 44, 46, 47, 48, 50};
        int target = 50;
        int result = fbiSearch(array, target);
        if(result == -1){
            System.out.println("没有找到目标值");
        }else{
            System.out.println("目标值的索引是: " + result);
        }
    }

    /**
     * 使用非递归的方式生成一个斐波那契数列，长度20
     * @return 长度为20的斐波那契数列
     */
    public static int[] fib(){
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for(int i=2; i<maxSize; i++){
            f[i] = f[i-1] + f[i-2];
        }
        // [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765]
        return f;
    }

    /**
     * 斐波那契查找
     * @param array 待查找数组
     * @param target 目标值
     * @return 目标值的索引
     */
    public static int fbiSearch(int[] array, int target){
        int left = 0;
        int right = array.length - 1;
        int k = 0; // 斐波那契数列的索引
        int mid = 0;
        int fib[] = fib();

        // 从斐波那契数列中找到接近array长度的值
        while (right+1>fib[k]){
            k++;
        }

        // 备份 array
        // Arrays.copyOf(array, fib[k]); 参数一：要复制的数组， 参数二：复制的长度
        // 因为f[k]值可能大于a的长度，不足的部分 Arrays.copyOf 方法会使用0补齐
        int[] temp = Arrays.copyOf(array, fib[k]);

        // 对 temp 数组进行最大值填充
        for(int i=right+1; i< temp.length; i++){
            temp[i] = array[right];
        }

        while (left <= right){
            mid = left + fib[k-1] -1;
            if(target<temp[mid]){ // 向左边找
                right = mid-1;
                k = k-1;
                /**
                 * 对k=k-1进行理解
                 * 1.全部元素=前面的元素+后面的元素
                 * 2.f[k]=k[k-1]+f[k-2]
                 * 因为前面有k-1个元素没所以可以继续分为f[k-1]=f[k-2]+f[k-3]
                 * 即在f[k-1]的前面继续查找k--
                 * 即下次循环,mid=f[k-1-1]-1
                 */
            }else if(target > temp[mid]){ // 向右边找
                left = mid + 1;
                k = k-2;
                /**
                 * 对k-=2理解
                 * 1.全部元素=前面的元素+后面的元素
                 * 2.f[k]=k[k-1]+f[k-2]
                 * 3.因为后面有k-2个元素，所以可以继续拆分f[k-2]=f[k-3]+f[k-4]
                 * 4.即在f[k-2]前面进行查找k-=2
                 * 5.即在下次循环mid=[k-1-2]-1
                 */
            }else{
                if(mid<=right){
                    return mid;
                }else {
                    return right;
                }
            }
        }
        return -1;
    }
}