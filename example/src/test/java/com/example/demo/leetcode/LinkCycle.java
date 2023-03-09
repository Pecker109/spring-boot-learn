package com.example.demo.leetcode;


import java.util.HashSet;
import java.util.Set;

/**
 * 给定一链表，判断链表中是否有环，存在返回 true
 */
public class LinkCycle {

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        //node5.next = node3;

        System.out.println(hasCycleBySet(node1));
        System.out.println(hasCycleByTwoPoint(node1));
    }

    /**
     * 算法 1:利用 set 打标记
     * 时间复杂度 O(n) 空间复杂度 O(n)
     *
     * @param head head
     * @return true-有环
     */
    private static boolean hasCycleBySet(ListNode head) {
        Set<Integer> set = new HashSet<>();
        while (null != head) {
            // set添加失败说明有元素了
            if (!set.add(head.value)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 算法 2:快慢指针:slow fast
     * 时间复杂度 O(n) 空间复杂度 O(1)
     * 这个算法的关键在于分析出:如果没有环,fast一定会先指向空,如果有环 fast 一定会等于 slow
     *
     * @param head head
     * @return true-有环
     */
    private static boolean hasCycleByTwoPoint(ListNode head) {
        if (null == head || null == head.next) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        // 如果没有环,fast一定会先指向空,如果有环 fast 一定会等于 slow
        // 所以跳出循环说明有环
        while (slow != fast) {
            if (null == fast || null == fast.next) {
                return false;
            }
            slow = slow.next;
            // 为了加速 fast 指针指向尾节点
            fast = fast.next.next;
        }
        return true;
    }


}
