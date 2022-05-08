package com.stanlong.leetcode;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class LeetCode {
    public static void main(String[] args) throws ParseException, IOException {

        Scanner scan = new Scanner(System.in);
        int ret = 0;
        int n = Integer.parseInt(scan.nextLine());
        String[] str = new String[n];

        for(int k = 0; k < n; k++) {
            str[k] = scan.nextLine();
        }

        int[][] matrix = new int[n][n];

        for(int i = 0; i < n; i++){
            String[] tmp = str[i].split(",");
            for(int j = 0; j < n; j++){
                matrix[i][j] = Integer.valueOf(tmp[j]);
            }
        }

        int sum = 0;
        int[] max_row = new int[n];
        int row_value = 0;
        for(int x = 0; x < n; x++){
            max_row[x] = 0;
            for(int z = 0; z < n; z ++) {    /*移动位数*/
                row_value = 0;
                for (int y = 0; y < n; y++) {
                    row_value += matrix[x][y] * Math.pow(2, (n - y - 1 + z)%n);
                }
                max_row[x] = Math.max(row_value, max_row[x]);
            }
            sum += max_row[x];
        }
        System.out.println(sum);
    }
}