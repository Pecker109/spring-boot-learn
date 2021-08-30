package com.example.demo.leetcode;


/**
 * @author Pecker
 * @Description 链表成环, 有环输出环点
 * @since 2021-04-17
 */
public class LinkCycleTest {

    //定义链表类
    public class ListNode {
        ListNode current;
        ListNode next;
    }

    //判断链表成环的方法
    ListNode get(ListNode node) {
        if (null == node) {
            return null;
        }
        ListNode fast = node.next;
        ListNode slow = node;
        while (slow.next != null && null != fast.next.next) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return slow;
            }

            if (fast.next == null) {
                return null;
            }
        }
        return null;
    }
}
