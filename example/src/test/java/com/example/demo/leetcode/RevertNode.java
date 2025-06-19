package com.example.demo.leetcode;

/**
 * 链表翻转: 1->2->3->4  翻转后: 4->3->2->1
 */
public class RevertNode {


    public static void main(String[] args) {

        /*
         算法就是一个方法:
         既然是个方法就要先确定方法名+入参+返回参数
         */
        ListNode node5 = new ListNode("5", null);
        ListNode node4 = new ListNode("4", node5);
        ListNode node3 = new ListNode("3", node4);
        ListNode node2 = new ListNode("2", node3);
        ListNode node1 = new ListNode("1", node2);

        //ListNode iterateNode = iterate(node1);
        //System.out.println(iterateNode);

        ListNode recursionNode = recursion(node1);
        System.out.println(recursionNode);
    }

    /**
     * 迭代法
     * 迭代法是从头往前,第一个元素就开始赋值处理了
     *
     * @param head 头节点
     * @return 翻转后的头结点
     */
    private static ListNode iterate(ListNode head) {
        // 定义出已知的元素
        ListNode prev = null;
        ListNode current = head;
        ListNode next;

        while (current != null) {
            // 最终赋值一定是个成环赋值
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    /**
     * 递归:大问题分解为相同的小问题
     * 递归法是一直递归调用到最后一个元素,从后往前开始处理.
     * 处理逻辑参照倒数第二和倒数第一节点的操作进行编码.
     * 返回值也很关键
     *
     * @param node 头节点
     * @return 翻转后的头结点
     */
    private static ListNode recursion(ListNode node) {
        /*
         * 递归 3 要素:
         * 递归目的: 返回最后一个节点(尾结点成了头节点)
         * 递归等式: 节点指针反转:当前节点的 next节点的next指针指向自己,当前节点原来的next指针清除
         * 递归出口: 当前节点没有next 节点了
         */
        // 递归:从最后一个元素开始
        if (node == null || node.next == null) {
            // 链表为空 或者 最后一个元素,返回最后一个元素
            return node;
        }
        // 具象思考的话,上面已经返回最后一个节点了,所以假设当前执行的 recursion 这就是在倒数第二个节点了
        // 倒数第二个节点的话,new_current = 最后一个节点
        ListNode new_head = recursion(node.next);
        // 链表指向翻转,新建的链表指向
        node.next.next = node;
        // 清除原来的链表指向
        node.next = null;
        // 返回的值一定是原链表的最后一个节点,而且一直是这个节点
        return new_head;
    }


}
