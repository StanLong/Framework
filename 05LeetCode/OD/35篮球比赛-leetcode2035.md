# 篮球比赛

**题目描述**

篮球（5V5）⽐赛中，每个球员拥有⼀个战⽃⼒，每个队伍的所有球员战⽃⼒之和为该队伍的总体战⽃⼒。现有10个球员准备分为两队进⾏训练赛，教练希望2个队伍的战⽃⼒差值能够尽可能的⼩，以达到最佳训练效果。给出10个球员的战⽃⼒，如果你是教练，你该如何分队，才能达到最佳训练效果？请输出该分队⽅案下的最⼩战⽃⼒差值。

**输⼊描述:**

- 10个篮球队员的战⽃⼒（整数，范围[1,10000]），战⽃⼒之间⽤空格分隔，如：10 9 8 7 6 5 4 3 2 1
- 不需要考虑异常输⼊的场景。

**输出描述:**

- 最⼩的战⽃⼒差值，如：1

**⽰例1**

```
输⼊
10 9 8 7 6 5 4 3 2 1
输出
1
```

**说明**

- 1 2 5 9 10分为⼀队，3 4 6 7 8分为⼀队，两队战⽃⼒之差最⼩，输出差值1。备注：球员分队⽅案不唯⼀，但最⼩战⽃⼒差值固定是1。

```java
package com.stanlong.leetcode;

public class LeetCode {

    public static void main(String[] args) {
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(diff(arr));
    }


    public static int diff(int[] array){
        int sum = 0;
        for (int k : array) {
            sum += k;
        }
        int[][] dp = new int[array.length+1][sum/2+1];
        for(int i=1; i<=array.length; i++){
            for(int j=1; j<=sum/2; j++){
                if(j-array[i-1]>=0){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-array[i-1]] + array[i-1]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return sum - 2*dp[array.length][sum/2];
    }
}
```

