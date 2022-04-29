package com.stanlong.leetcode;

import java.util.Arrays;

public class LeetCode {
    public static void main(String[] args)  {
        int[] nums = {1,4,6,8,10};
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.getSumAbsoluteDifferences(nums)));
    }
}

class Solution{
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int temp = 0;
        int[] result = new int[nums.length];
        for(int i=0; i< nums.length; i++){
            for(int j=0; j< nums.length; j++){
                temp = temp + Math.abs(nums[i]-nums[j]);
            }
            result[i] = temp;
            temp = 0;
        }
        return result;
    }
}