package com.stanlong.leetcode;

public class LeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode result = solution.addTwoNumbers(l1, l2);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    ListNode head = new ListNode(); // 头节点
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 解题思想：因为l1和l2都是逆序存储， 因此l1和l2对应节点顺序相加，即相当于整数个位、十位、百位。。。相加。
        // 注意整数相加要考虑进位的问题， 按题目意思【每个节点只能存储一位数字】， 则数字最大值为9，和最大值为18，进位值最大1
        // 最高位相加也可能有进位
        ListNode temp = head; // 辅助节点，帮助遍历
        int carry = 0; // 进位值，只有0,1两个取值
        while (l1 != null || l2 != null){
            int val1 = l1 != null ? l1.val:0;
            int val2 = l2 != null ? l2.val:0;
            int result = val1 + val2 + carry;
            carry = result / 10; // 进位

            // 注意理解这三行代码
            ListNode sumNode = new ListNode(result % 10);
            temp.next = sumNode;
            temp = sumNode;

            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
        }
        if(carry == 1){ // 如果最高位相加还有进位，把该进位加到节点中
            temp.next = new ListNode(1);
        }
        return head.next;
    }
}