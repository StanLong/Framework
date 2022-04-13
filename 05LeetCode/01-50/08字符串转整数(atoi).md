# 字符串转整数(atoi)

请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。

函数 myAtoi(string s) 的算法如下：

1. 读入字符串并丢弃无用的前导空格
2. 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
3. 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
4. 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
5. 如果整数数超过 32 位有符号整数范围 $[−2^{31},  2^{31} − 1]$ ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 $−2^{31}$ 的整数应该被固定为 $−2^{31}$ ，大于 $2^{31} − 1$ 的整数应该被固定为 $2^{31} −1$  。
6. 返回整数作为最终结果。

**注意：**

- 本题中的空白字符只包括空格字符 ' ' 。
- 除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。

**示例 1：**

```
输入：s = "42"
输出：42
解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
第 1 步："42"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
         ^
第 3 步："42"（读入 "42"）
           ^
解析得到整数 42 。
由于 "42" 在范围 [-231, 231 - 1] 内，最终结果为 42 。
```

**示例 2：**

```
输入：s = "   -42"
输出：-42
解释：
第 1 步："   -42"（读入前导空格，但忽视掉）
            ^
第 2 步："   -42"（读入 '-' 字符，所以结果应该是负数）
             ^
第 3 步："   -42"（读入 "42"）
               ^
解析得到整数 -42 。
由于 "-42" 在范围 [-231, 231 - 1] 内，最终结果为 -42 。
```

**示例 3：**

```
输入：s = "4193 with words"
输出：4193
解释：
第 1 步："4193 with words"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："4193 with words"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
         ^
第 3 步："4193 with words"（读入 "4193"；由于下一个字符不是一个数字，所以读入停止）
             ^
解析得到整数 4193 。
由于 "4193" 在范围 [-231, 231 - 1] 内，最终结果为 4193 。
```

**提示：**

```
0 <= s.length <= 200
s 由英文字母（大写和小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
```

```java
package com.stanlong;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串转整数
 * 解法1： 使用正则表达式
 */
public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.myAtoi("01"));
    }

}

/**
 * 字符串转整数
 * 使用正则表达式求解
 */
class Solution {
    public int myAtoi(String str) {
        str = str.trim();
        int res = 0;
        Pattern p = Pattern.compile("^[\\-\\+]?\\d+");
        Matcher m = p.matcher(str);
        if(m.find()){
            try{
                res = Integer.parseInt(str.substring(m.start(), m.end()));
            }catch (Exception e){
                res = str.charAt(0) == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        return res;
    }
}
```

```java
package com.stanlong;

/**
 * 字符串转整数
 * 解法2：
 */
public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.myAtoi("01"));
    }

}

/**
 * 字符串转整数
 */
class Solution {
    public int myAtoi(String str) {
        int index = 0;
        int len = str.length();
        
        char[] chars = str.toCharArray();

        // 1、去除前导空格
        while (index < len && chars[index] == ' '){
            index++;
        }

        // 2、如果已经遍历完成（针对极端用例 "      "）
        if(index == len){
            return 0;
        }

        // 3、如果出现符号字符，仅第 1 个有效，并记录正负
        int sign = 1;
        char firstChar = chars[index];
        if(firstChar == '+'){
            index++;
        }else if(firstChar == '-'){
            sign = -1;
            index++;
        }

        // 4、将后续出现的数字字符进行转换
        // 不能使用 long 类型，这是题目说的
        int res = 0;
        while (index < len){
            char currChar = chars[index];
            // 4.1 先判断不合法的情况
            if(currChar > '9' || currChar < '0'){
                break;
            }

            // 题目中说：环境只能存储 32 位大小的有符号整数，因此，需要提前判：断乘以 10 以后是否越界
            if(res > Integer.MAX_VALUE/10 || (res == Integer.MAX_VALUE / 10 && (currChar-'0') > Integer.MAX_VALUE % 10)){
                return Integer.MAX_VALUE;
            }

            if(res < Integer.MIN_VALUE/10 || (res == Integer.MIN_VALUE / 10 && (currChar-'0') > -(Integer.MIN_VALUE % 10))){
                return Integer.MIN_VALUE;
            }

            // 4.2 合法的情况下，才考虑转换，每一步都把符号位乘进去
            res = res * 10 + sign * (currChar-'0');
            index++;
        }
        return res;



    }
}
```



