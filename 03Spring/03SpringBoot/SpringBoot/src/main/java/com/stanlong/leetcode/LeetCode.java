package com.stanlong.leetcode;

public class LeetCode {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {1,1,1,0,0,0,1,1,1,1,0};
        int k = 2;
        System.out.println(s.findMaxConsecutiveOnes(nums, k));
    }
}

class Solution {
    public int findMaxConsecutiveOnes(int[] nums, int k) {
        int len = nums.length;
        int left = 0;
        int right = 0;
        int max = 0;
        int counter = 0;

        for (int num : nums) {
            while (right < len && counter < k) {
                max = Math.max(max, left - right);
                if (num == 0) {
                    counter++;
                }
                right++;
            }
            while (left < right && counter >= k) {
                if (nums[left] == 0) {
                    counter--;
                }
                left++;
            }
        }
        return max;
    }
}