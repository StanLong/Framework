package com.stanlong.leetcode;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "aa";
        String p = "a*";
        System.out.println(solution.isMatch(s, p));

    }
}

class Solution {
    public boolean isMatch(String s, String p){
        if (p.isEmpty()){
            return s.isEmpty();
        }
        boolean firstMatch = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');

        if (p.length() > 1 && p.charAt(1) == '*'){
            return isMatch(s, p.substring(2)) || (firstMatch && isMatch(s.substring(1), p));
        } else{
            return firstMatch && isMatch(s.substring(1), p.substring(1));
        }
    }
}
