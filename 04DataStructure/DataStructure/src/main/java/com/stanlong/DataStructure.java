package com.stanlong;

/**
 * 二分查找， 非递归方式实现
 */
public class DataStructure {
    public static void main(String[] args) {
        int[] array = {2, 3, 4, 5, 15, 19, 26, 27, 36, 38, 44, 46, 47, 48, 50};
        int target = 51;
        int index = binarySearch(array, target);
        if(index == -1){
            System.out.println("没有找到目标值");
        }else{
            System.out.println("目标值的索引是: " + index);
        }
    }

    public static int binarySearch(int[] nums, int target){
        int left = 0;
        int right = nums.length-1;
        int mid;
        while (left <= right){
            mid = (left + right) / 2;
            if(target == nums[mid]){
                return mid;
            }else if(target > nums[mid]){
                left = mid+1;
            }else if(target < nums[mid]){
                right = mid-1;
            }
        }
        return -1;
    }


}
