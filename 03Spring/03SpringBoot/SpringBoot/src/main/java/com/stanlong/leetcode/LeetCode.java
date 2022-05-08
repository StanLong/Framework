package com.stanlong.leetcode;

import java.util.Scanner;

public class LeetCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int n = sc.nextInt();
            int m = sc.nextInt();
            String[] tmp = sc.next().split(" ");
            int[] ms = new int[tmp.length];
            for(int i=0; i< tmp.length; i++){
                ms[i] = Integer.parseInt(tmp[i]);
            }
            int k = sc.nextInt();

        }
    }
}