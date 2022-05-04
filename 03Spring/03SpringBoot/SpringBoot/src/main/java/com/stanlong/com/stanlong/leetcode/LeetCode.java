package com.stanlong.com.stanlong.leetcode;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "aa";
        String p = "a*";
        System.out.println(solution.isMatch(s, p));

    }
}

class Solution{
    public boolean isMatch(String s, String p){
        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m+1][n+1];

        // 动态转移方程初始值
        dp[0][0] = true;

        for(int i=0; i<=m; i++){
            for(int j=1; j<= n; j++){
                if(p.charAt(j-1) == '*'){
                    dp[i][j] = dp[i][j-2];
                    if(ismatch(s, p, i, j-1)){
                        dp[i][j] = dp[i][j] || dp[i-1][j];
                    }
                }else{
                    if(ismatch(s, p, i, j)){
                        dp[i][j] = dp[i-1][j-1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    public boolean ismatch(String s, String p, int i, int j){
        if(i==0){
            return false;
        }
        if(p.charAt(j-1) == '.'){
            return true;
        }
        return s.charAt(i-1) == p.charAt(j-1);
    }
}