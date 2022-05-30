# URL 拼接

**题目描述：**

- 给定一个url前缀和url后缀,通过,分割 需要将其连接为一个完整的url
- 如果前缀结尾和后缀开头都没有/，需要自动补上/连接符
- 如果前缀结尾和后缀开头都为/，需要自动去重
- 约束：不用考虑前后缀URL不合法情况


**输入描述**

- url前缀(一个长度小于100的字符串) url后缀(一个长度小于100的字符串)

**输出描述：**

- 拼接后的url

**示例1**

```
输入 /acm,/bb
输出 /acm/bb
```

**示例2**

```
输入 /abc/,/bcd
输出 /abc/bcd
```

**示例3**

```
输入 /abc,bef
输出 /acd/bef
```

**示例4**

```
输入 , 输出 /
```

```java
package com.stanlong.leetcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = ",";
        System.out.println(solution.isSubsequence(s));

    }
}

/**
 * 正则表达式匹配
 */
class Solution {
    public String isSubsequence(String s){
        Pattern pattern = Pattern.compile("/?,/?");
        Matcher matcher = pattern.matcher(s);
        String result = matcher.replaceAll("/");
        return result;
    }
}
```

```java
package com.stanlong.leetcode;

import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(str.replaceAll("/?,/?", "/"));
    }
}
```

