# 补种未成活胡杨

某沙漠新种植N颗胡杨（编号1~N），排成一排。一个月后，有M颗未能成活。现可补种K颗（只可补种，不可新种），请问怎样补种，可以得到最多的连续胡杨树？ 

**输入** 

- N 总种植数量 
- M 未成活数量 
- M个空格分割的数，按编号从小到大排列 
- K 最多可以补种的数量 
- 其中 1<=N<1000， 1<=M<N， 0<=K<=M 

**输出**

最多的连续胡杨棵树

**示例1**

```
输入 
10 
3 
2 4 7 
1 
输出 
6 
```

**说明** 

补种第7颗 可得到5，6，7，8，9，10连续胡杨树

**示例2**

```
输入
5
2
2 4
1
输出
3
```

```java
package com.stanlong.leetcode;

import java.util.Arrays;
import java.util.Scanner;

public class LeetCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int m = Integer.parseInt(scanner.nextLine());
        String[] ms = scanner.nextLine().split(" ");
        int k = Integer.parseInt(scanner.nextLine());

        boolean[] b = new boolean[n];
        Arrays.fill(b, true);

        for (String s : ms) {
            b[Integer.parseInt(s) - 1] = false;
        }

        int left = 0;
        int right = 0;
        int max = 0;
        int counter = 0;

        while (right<n){
            if(b[right] == false){
                counter++;
            }
            right++;
            while (counter>k){
                if(b[left] == false){
                    counter--;
                }
                left++;
            }
            max = Math.max(max, (right-left));
        }
        System.out.println(max);

    }
}
```

