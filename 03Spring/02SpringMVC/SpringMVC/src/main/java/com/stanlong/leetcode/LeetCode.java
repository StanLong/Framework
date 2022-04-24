package com.stanlong.leetcode;

import java.util.Scanner;

public class LeetCode {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String tag = in.nextLine();
        String[] tlv = in.nextLine().split(" ");
        for(int i=0; i< tlv.length;){
            int length = Integer.parseInt(tlv[i+2] + tlv[i+1], 16);
            if(tag.equals(tlv[i])){
                StringBuilder sb = new StringBuilder();
                for(int j=i+3; j<i+3+length; j++){
                    sb.append(tlv[j]).append(" ");
                }
                System.out.println(sb.toString());
                break;
            }else {
                i = i + length + 3;
            }
        }
    }
}
