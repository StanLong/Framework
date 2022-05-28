package com.stanlong.leetcode;

import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String hex = scanner.nextLine();
        int result = Integer.valueOf(hex, 16);
        System.out.println(result);


    }


}