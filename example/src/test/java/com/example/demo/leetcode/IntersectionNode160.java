package com.example.demo.leetcode;

import java.util.Optional;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * 例如:链表 A:a1 a2 c1 c2 c3 联表 B: b1 b2 b3 c1 c2 c3, 都包含 c1 c2 c3,则相交,返回 c1
 */
public class IntersectionNode160 {
    public static void main(String[] args) {
        // 相交
        ListNode c3 = new ListNode("c3", null);
        ListNode c2 = new ListNode("c2", c3);
        ListNode c1 = new ListNode("c1", c2);

        ListNode a2 = new ListNode("a2", c1);
        ListNode a1 = new ListNode("a1", a2);

        ListNode b3 = new ListNode("b3", c1);
        ListNode b2 = new ListNode("b2", b3);
        ListNode b1 = new ListNode("b1", b2);

        // 不相交
        //ListNode a2 = new ListNode("a2", null);
        //ListNode a1 = new ListNode("a1", a2);
        //
        //ListNode b2 = new ListNode("b2", null);
        //ListNode b1 = new ListNode("b1", b2);

        System.out.println(Optional.ofNullable(intersectionNode(a1, b1)).orElse(new ListNode(null, null)).value);
    }


    public static ListNode intersectionNode(ListNode headA, ListNode headB) {
        if (null == headA || null == headB) {
            return null;
        }

        /*
        双指针法:pA 指向 nodeA 头结点,pB 指向 nodeB 头结点,两指针同时移动,pA 和 pB 相等时相交,
        pA 或 pB 为空时移动到下一个另外一个节点的头结点,
        如果移动到最后还是不相交,也都会是 null.
        相交:相交处一定会重合
        pA 移动轨迹:a1 a2 c1 c2 c3 -> b1 b2 b3 c1 c2 c3
        pB 移动轨迹:b1 b2 b3 c1 c2 c3 -> a1 a2 c1 c2 c3
        不相交: 相交处为 null
        pA 移动轨迹:a1 a2 b1 b2 null
        pB 移动轨迹:b1 b2 a1 a2 null
         */
        //ListNode pA = nodeA, pB = nodeB;
        //while (!pA.value.equals(pB.value)) {
        //    pA = pA.next == null ? nodeB : pA.next;
        //    pB = pB.next == null ? nodeA : pB.next;
        //}

        ListNode pA = headA, pB = headB;
        // 要么有相交pA=pB,要么不相交pA=pB=null
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

}
