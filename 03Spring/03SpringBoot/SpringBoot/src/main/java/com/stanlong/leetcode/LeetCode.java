package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for(int i=0; i< nums.length; i++){
            nums[i] = scanner.nextInt();
        }

        int sum = 0;
        int left = 0;
        int right = 1;
        while (right < n){
            if(nums[right]+nums[left]<100){
                sum = 100-(nums[left] + nums[right]);
            }
            left++;
            right++;
        }
        System.out.println(sum);


    }
}