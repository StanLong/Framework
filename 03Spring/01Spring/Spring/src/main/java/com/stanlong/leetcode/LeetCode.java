package com.stanlong.leetcode;

import java.util.*;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "Aabb";
        System.out.println(solution.frequencySort(s));

    }
}

class Solution {
    public String frequencySort(String s){
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0; i< chars.length; i++){
            map.put(chars[i], map.getOrDefault(chars[i], 0)+1);
        }
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> {
            return o2.getValue().compareTo(o1.getValue());
        });
        Iterator<Map.Entry<Character, Integer>> it = list.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()){
            Map.Entry<Character, Integer> entry = it.next();
            for(int i=0; i<entry.getValue(); i++){
                sb.append(entry.getKey());
            }
        }
        return sb.toString();
    }
}