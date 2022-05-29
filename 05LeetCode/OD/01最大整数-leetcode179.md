# 最大数

- 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
- 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。

 **示例 1：**

```java
输入：nums = [10,2]
输出："210"
```

**示例 2：**

```java
输入：nums = [3,30,34,5,9]
输出："9534330"
```

**提示：**

```java
1 <= nums.length <= 100
0 <= nums[i] <= 109
```

```java
package com.stanlong.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] strs = scanner.nextLine().split(",");
        List<String> list = new ArrayList<>();
        for(String str : strs){
            list.add(str);
        }
        list.sort((o1, o2) -> {
            String str1 = o1+o2;
            String str2 = o2+o1;
            return str2.compareTo(str1);
        });

        StringBuilder sb = new StringBuilder();
        for(String str : list){
            sb.append(str);
        }
        System.out.println(sb.toString());
    }
}
```

