package com.stanlong.leetcode;

import java.util.*;

public class LeetCode {

    public static void main(String[] args) throws Exception {
        int[] array = {1,3,3,3,2,4,4,4,5};
        int[] result = countSort(array);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 计数排序
     * @param array 待排序数组
     */
    public static int[] countSort(int array[]){
        // 1. 获取原数组中最大的值
        int max = Integer.MIN_VALUE;
        for(int data : array){
            max = Math.max(max, data);
        }

        // 2. 初始化计数数组
        // 注意这里计数数组的长度是 max+1, 这样计数数组的最大索引正好是array中的最大值
        int[] count = new int[max+1];

        // 3. 将原数组的值转化到计数数组中
        // array 的值 为 count 的索引
        // array 的值的个数 为 count 的值
        for(int data : array){
            count[data]++ ;
        }

        // 4. 创建结果数组
        int[] result = new int[array.length];

        // 结果数组索引
        int index = 0;

        Map<Integer, Integer> map = new HashMap<>();
        // 5. 将计数数组中值不为0的索引保存到结果数组中
        for(int i=0; i< count.length; i++){
            map.put(i, count[i]);
        }

        for(Integer key : map.keySet()){
            System.out.println(key + ":" + map.get(key));
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Iterator<Map.Entry<Integer, Integer>> it = list.iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Integer> next = it.next();
            System.out.println(next.getKey());
        }



        return result;
    }
}
