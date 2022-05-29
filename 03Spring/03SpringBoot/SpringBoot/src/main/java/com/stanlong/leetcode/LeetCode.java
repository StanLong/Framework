package com.stanlong.leetcode;

import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] str = scanner.nextLine().split(",");
        Map<String, Integer> map = new HashMap<>();
        for(String c : str){
            map.put(c, map.getOrDefault(c, 0)+1);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Integer> entry: list){
            sb.append(entry.getKey());
            sb.append(",");
        }
        System.out.println(sb.toString().replaceAll(",$", ""));

    }
}