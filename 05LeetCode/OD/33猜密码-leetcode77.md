# 猜密码

小杨申请了一个保密柜，但是他忘记了密码，只记得密码都是数字，而且所有数字都是不重复的。请你根据他记住的数字范围和密码的最小数字数量，帮他算一下有哪些可能的组合。规则如下：

- 1、输出的组合都是从可选的数字范围中选取的，且不能重复。
- 2，输出的密码数字要按照从小到大的顺序排列，密码组合需要按照字母顺序，从小到大的顺序排序。
- 3、输出的每一个组合的数字的数量要大于等于密码最小数字数量；
- 4、如果可能的组合为空，则返回“None”

**输入描述：**

- 1、输入的第一行是可能的密码数字列表，数字间以半角逗号分隔
- 2、输入的第二行是密码最小数字数量

**输出描述：**

- 可能的密码组合，每种组合显示成一行，每个组合内部的数字以半角逗号分隔，以从小到大的顺序排列。
- 输出的组合间需要按照字典排序，比如：2,3,4放到2,4的前面

**备注：**

```
字典序是指按照单词出现在字典的顺序进行排序的方法，比如：
a排在b前
a排在ab前
ab排在ac前
ac排在aca前
```

**示例1：**

```
输入
2,3,4
2

输出
2,3
2,3,4
2,4
3,4
```

```java
package com.stanlong.leetcode;

import java.util.*;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] strs = scanner.nextLine().split(",");
        int k = Integer.parseInt(scanner.nextLine());
        int[] nums = new int[strs.length];
        for(int i=0; i< nums.length; i++){
            nums[i] = Integer.parseInt(strs[i]);
        }
        Arrays.sort(nums);
        List<List<Integer>> resultList = new ArrayList<>();
        while(k<= nums.length){
            List<Integer> path = new ArrayList<>();
            combine(nums, k, 0, path, resultList);
            k++;
        }

        SortedSet<String> ts = new TreeSet<>();
        for(List<Integer> tempList : resultList){
            StringBuilder sb = new StringBuilder();
            for(int data : tempList){
                sb.append(data);
                sb.append(",");
            }
            ts.add(sb.toString().replaceAll(",$", ""));
        }
        if(ts.size() > 0){
            for (String t : ts) {
                System.out.println(t);
            }
        }else {
            System.out.println("None");
        }

    }

    public static void combine(int[] nums, int k, int startIndex, List<Integer> path, List<List<Integer>> resultList){
        if(path.size() == k){
            resultList.add(new ArrayList<>(path));
            return;
        }
        for(int i=startIndex; i<nums.length; i++){
            path.add(nums[i]);
            combine(nums, k, i+1, path, resultList);
            path.remove(path.size()-1);
        }

    }
}
```

