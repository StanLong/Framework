package com.stanlong;

/**
 * 整数反转
 * 准备知识：
 * 32 位有符号整数的取值范围[-2147483648, 2147483647] , 长度为10位
 */
public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.reverse(100));
    }

}

class Solution {
    public int  reverse(int x) {
        int res = 0;
        while( x != 0){
            // 每次取末尾数字
            int temp = x % 10;
            // 判断是否大于最大32位整数
            if(res > Integer.MAX_VALUE / 10 || res < Integer.MIN_VALUE / 10){
                return 0;
            }
            res = res * 10 + temp;
            x = x / 10;
        }
        return res;
    }
}
