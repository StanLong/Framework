# 数组去重的排序

给定一个乱序数组，数组大小不超过100。 删除所有重复元素，使得每个元素只出现一次，并且按照出现次数由高到低排序。 相同出现次数 按照第一次出现的顺序进行先后排序要求稳定）

**示例 1:**

```
输入: 1,3,3,3,2,4,4,4,5
输出: 3,4,1,2,5
```

```java
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
```

