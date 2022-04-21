package com.stanlong.leetcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = ",";
        System.out.println(solution.isSubsequence(s));

    }
}

/**
 * 正则表达式匹配
 */
class Solution {
    public String isSubsequence(String s){
        Pattern pattern = Pattern.compile("/?,/?");
        Matcher matcher = pattern.matcher(s);
        String result = matcher.replaceAll("/");
        result.substring(1, result.length()-1);
        return result;
    }
}