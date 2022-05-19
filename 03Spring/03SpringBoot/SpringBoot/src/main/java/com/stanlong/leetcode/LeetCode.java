package com.stanlong.leetcode;

import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        String[] str = scanner.nextLine().split(" ");
        int[] nums = new int[m];
        for(int i=0; i< str.length; i++){
            nums[i] = Integer.parseInt(str[i]);
        }
        int[] result = new int[m];
        for(int i=0; i<m; i++){
            for(int j=i+1; j<m; j++){
                if(nums[j] > nums[i]){
                    result[i]=j;
                    break;
                }
                if(j==m-1){
                    result[i] = 0;
                }
            }
        }
        System.out.println(Arrays.toString(result));

    }
}
