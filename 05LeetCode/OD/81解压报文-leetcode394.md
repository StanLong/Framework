# 解压报文

- 为了提升数据传输的效率，会对传输的报文进行压缩处理。输入一个压缩后的报文，请返回它解压后的原始报文。

- 压缩规则：n[str]，表示方括号内部的 str 正好重复 n 次。注意 n 为正整数（0 < n <= 100），str只包含小写英文字母，不考虑异常情况。

**输入描述:**

- 输入压缩后的报文：
- 1）不考虑无效的输入，报文没有额外的空格，方括号总是符合格式要求的；
- 2）原始报文不包含数字，所有的数字只表示重复的次数 n ，例如不会出现像 5b 或 3[8] 的输入；

**输出描述:**

- 解压后的原始报文
- 注：1）原始报文长度不会超过1000，不考虑异常的情况

**示例1**

```
输入
3[k]2[mn]

输出
kkkmnmn

说明 k 重复3次，mn 重复2次，最终得到 kkkmnmn
```

**示例2**

```
输入
3[m2[c]]

输出
mccmccmcc

说明 m2[c] 解压缩后为 mcc，重复三次为 mccmccmcc”
```

```java
package com.stanlong.leetcode;

import java.util.Scanner;

public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(deCodeString(str));
    }

    public static String deCodeString(String str){
        // 递归出口
        if(onlyString(str)){
            System.out.println(str);
            return str;
        }

        int index = 0;
        int len = str.length();

        StringBuilder sb = new StringBuilder();
        while (index < len){
            char ch = str.charAt(index);
            if('a' <= ch && ch <= 'z'){
                sb.append(ch);
                index++;
            }else {
                int num = 0;
                int left = index;
                int right = index;
                while (right<len && str.charAt(right) >= '0' && str.charAt(right) <= '9'){
                    right++;
                }
                num = Integer.parseInt(str.substring(left, right)); // 取出数字
                left = right;
                right++;
                int leftP = 1;
                while (true){
                    char temp = str.charAt(right);
                    if(temp == '['){
                        leftP++;
                    }else if(temp == ']'){
                        leftP--;
                    }

                    if(leftP == 0){
                        break;
                    }
                    right++;
                }
                String smallFrac = deCodeString(str.substring(left+1, right));
                for(int i=0; i<num; i++){
                    sb.append(smallFrac);
                }
                index = right+1;
            }
        }
        return sb.toString();
    }

    /**
     * 字符串中不包含 '[' 或者  ']'时直接返回字符串
     */
    public static boolean onlyString(String s){
        int index = 0;
        while(index != s.length()){
            char ch = s.charAt(index);
            if(ch == '[' || ch == ']'){
                return false;
            }
            index++;
        }
        return false;
    }
}
```

