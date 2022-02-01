package com.stanlong;

import java.util.ArrayList;

/**
 * 二分查找
 * 返回所有目标值的索引
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        int[] array = {2, 3,3,3,3, 4, 5, 15, 19, 26, 27, 36, 38, 44, 46, 47, 48, 50};
        int left = 0;
        int right = array.length-1;
        int target = 3;
        ArrayList<Integer> arrayList = binarySearch(array, left, right,target);
        if(arrayList.isEmpty()){
            System.out.println("没有找到目标值");
        }else{
            System.out.println("目标值索引是: " + arrayList.toString());
        }
    }

    /**
     * 二分查找
     * @param array 待查找数组
     * @param left 最小索引
     * @param right 最大索引
     * @param target 目标值
     * @return 返回目标值的索引
     */
    public static ArrayList<Integer> binarySearch(int[] array, int left, int right, int target){
        // 递归结束条件
        if(left > right){
            return new ArrayList<Integer>();
        }
        int mid = (left+right)/2;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        if(array[mid] > target){ // 递归左边
            return binarySearch(array, left, mid-1, target);
        } else if(array[mid] < target){ // 递归右边
            return binarySearch(array, mid+1, right, target);
        }else {
            // 先保存中间值到列表里
            arrayList.add(mid);
            int temp = mid-1;
            while (true){
                if(temp < 0 || array[temp] != target){
                    break;
                }else{
                    arrayList.add(temp);
                    temp = temp - 1;
                }
            }
            temp = mid+1;
            while (true){
                if(temp >= array.length || array[temp] != target){
                    break;
                }else{
                    arrayList.add(temp);
                    temp = temp + 1;
                }
            }
        }
        return arrayList;
    }
}