package com.stanlong.leetcode;

import java.util.Scanner;

/**
 * 学生方阵
 */
public class LeetCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] strings = scanner.nextLine().split(",");
        int m = Integer.parseInt(strings[0]);
        int n = Integer.parseInt(strings[1]);
        String[][] matrix = new String[m][n];
        for(int i=0; i<matrix.length; i++){
            String str = scanner.nextLine();
            for(int j=0; j<matrix[i].length; j++){
                matrix[i][j] = str.split(",")[j];
            }
        }

    }
}