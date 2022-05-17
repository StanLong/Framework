package com.stanlong.leetcode;

public class LeetCode {

    public static void main(String[] args) {
        String binaryA = Integer.toBinaryString(3);
        System.out.println(binaryA);
        System.out.println(Integer.valueOf(binaryA));
        String hexA = String.format("%08d", Integer.valueOf(binaryA));
        System.out.println(hexA);
    }
}
