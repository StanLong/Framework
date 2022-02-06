**1.== 和 equals 的区别是什么？**

- **== 解读**

对于基本类型和引用类型 == 的作用效果是不同的

​	基本类型：比较的是值是否相同；

​	引用类型：比较的是引用是否相同；

- **equals 解读**

equals 本质上就是 ==，只不过 String 和 Integer 等重写了 equals 方法，把它变成了值比较

**2.HashMap 和 Hashtable 有什么区别**

- hashTable同步的，而HashMap是非同步的，效率上逼hashTable要高

- hashMap允许空键值，而hashTable不允许

**3.ArrayList 和 LinkedList 的区别是什么**

最明显的区别是 ArrrayList底层的数据结构是数组，支持随机访问，而 LinkedList 的底层数据结构是双向循环链表，不支持随机访问。使用下标访问一个元素，ArrayList 的时间复杂度是 O(1)，而 LinkedList 是 O(n)。

**4.Iterator 和 ListIterator 有什么区别**

- Iterator可用来遍历Set和List集合，但是ListIterator只能用来遍历List
- Iterator对集合只能是前向遍历，ListIterator既可以前向也可以后向
- ListIterator实现了Iterator接口，并包含其他的功能，比如：增加元素，替换元素，获取前一个和后一个元素的索引，等等

