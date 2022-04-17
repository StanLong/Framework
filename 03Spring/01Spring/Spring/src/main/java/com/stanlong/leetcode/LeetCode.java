package com.stanlong.leetcode;

import java.util.Stack;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "deeedbbcccbdaa";
        int k = 3;
        System.out.println(solution.removeDuplicates(s, k));

    }
}

/**
 * 解题思路，用栈计数
 */
class Solution {
    public String removeDuplicates(String s, int k){
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder(s); // 将字符串转换成StringBuilder类型，方便进行删除操作
        for(int i=0; i<sb.length();++i){
            if(i==0 || sb.charAt(i) != sb.charAt(i-1)){
                stack.push(1);
            }else {
                int incremented = stack.pop() + 1;
                if(incremented==k){
                    sb.delete(i-k+1, i+1);
                    i = i-k;
                }else {
                    stack.push(incremented);
                }
            }
        }
        return sb.toString();
    }
}
