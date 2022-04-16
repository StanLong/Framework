# 最大数

给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。

注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。

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
import java.util.Comparator;
import java.util.List;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3,30,34,5,9};
        String result = solution.largestNumber(nums);
        System.out.println(result);
    }
}

class Solution {
    public String largestNumber(int[] nums) {
        List<String> list = new ArrayList<>();
        for(int num : nums){
            list.add(num + "");
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String str1 = o1 + o2;
                String str2 = o2 + o1;
                return str2.compareTo(str1);
            }
        });
        StringBuilder sb = new StringBuilder();
        for(String data : list){
            sb.append(data);
        }
        if(sb.charAt(0) == '0'){
            return "0";
        }
        return sb.toString();
    }
}
```

