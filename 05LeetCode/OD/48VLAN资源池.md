# VLAN 资源池

- VLAN是一种为局域网设备进行逻辑划分的技术，为了标识不同的VLAN 引入了VLAN id(1~4094之间的整数)。
- 定义一个VLAN id 的资源池， 资源池中连续的VLAN用开始VLAN-结束VLAN表示，不连续的用单个整数表示，所有的VLAN用英文逗号连接起来。
- 现有一个VLAN资源池，业务需要从资源池中申请一个VLAN， 需要你输出从VLAN资源池中移除申请的VLAN后的资源池。

**输入描述**

- 第一行为字符串格式的VLAN资源池
- 第二行为业务要申请的VLAN，VLAN的取值范围1~4094

**输出描述**

- 从输入VLAN资源池中移除申请的VLAN后字符串格式的VLAN资源池
- 输出要求满足题目中要求的格式，并且要求从小到大升序输出
- 如果申请的VLAN不在原资源池，输出升序排序的原资源池的字符串即可

 **示例一**

```
输入
1-5
2

输出
1,3-5

说明：原VLAN资源池中有1 2 3 4 5 移除2后, 剩下的1 3 4 5按照升序排列的方式为 1 3-5
```

**示例二**

```
输入
20-21,15,18,30,5-10
15

输出
5-10,18,20-21,30

说明：原VLAN资源池中有5 6 7 8 9 10 15 18 20 21 30,移除15后剩下的为 5 6 7 8 9 10 18 20 21 30, 按照题目描述格式并升序后的结果为5-10,18,20-21,30
```

**示例三**

```
输入
5,1-3
10

输出
1-3,5
```

```java
package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class LeetCode {
    public static void main(String[] args) {
        // 1. 处理输入
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        String[] strings = scanner.nextLine().split(",");
        String target = scanner.nextLine();

        // 2. 先处理数据

        // 3. 再排序
    }
}
```

