package com.stanlong.leetcode;

import java.util.HashSet;

public class LeetCode {
    public static void main(String[] args) {
        String s = "abcabcbb";
        Solution solution = new Solution();
        int result = solution.lengthOfLongestSubstring(s);
        System.out.println(result);
    }
}

/**
 * 解题思想：滑窗思想
 * 注意这题是要返回 不重复字符的最长子串的长度， 不是返回不重复字符的最长子串
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int res = 0; // 最长字串
        int left = 0; // 左指针
        int right = 0; // 右指针， 初始情况下左右指针指向同一个位置
        HashSet<Character> t = new HashSet<>();
        while (right < s.length()){
            if(!t.contains(s.charAt(right))){ // 按下标遍历字符传中的字符
                t.add(s.charAt(right)); // 把字符加入到HashSet中
                right++; // 下一个字符
                res = Math.max(res, t.size()); // 统计 HashSet 中字符的个数
            }else{
                // 注意这题是要返回 不重复字符的最长子串的长度， 不是返回不重复字符的最长子串
                t.remove(s.charAt(left)); // 如果下一个字符在HashSet中已存在，则从HashSet中一个一个删除即可。
                left++;
            }
        }
        return res;
    }
}