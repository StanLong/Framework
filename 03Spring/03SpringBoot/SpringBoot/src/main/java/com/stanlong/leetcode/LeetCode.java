package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int target = scanner.nextInt();
        int[] nums = new int[target];
        for(int i=0; i< nums.length; i++){
            nums[i] = scanner.nextInt();
        }
        int A = 0;
        int B = 0;
        int C = 0;
        boolean flag = false;
        for(int i=0; i< nums.length; i++){
            A = nums[i];
            for(int j=nums.length-i-1; j>= 0; j--){
                B = nums[j];
                C = nums[nums.length-i-j-1];
                if(A == B + 2 * C &&(A!=B) &&(A!=C) && (B!=C)){
                    flag = true;
                    System.out.println(A + " " + B + " " + C);
                    break;
                }
            }
        }
        if(!flag){
            System.out.println(0);
        }
    }
}