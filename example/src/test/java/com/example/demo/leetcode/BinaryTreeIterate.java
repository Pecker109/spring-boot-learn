package com.example.demo.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树遍历
 * 遍历顺序:
 * 1.前序遍历:根左右
 * 2.中序遍历:左根右
 * 3.后序遍历:左右根
 * 4.层序遍历:从上往下,从左往右
 * 遍历方法:
 * 1.递归
 * 2.迭代,使用迭代方法实现递归函数,与递归等价
 * 3.Morris 遍历
 */
public class BinaryTreeIterate {
    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(5, null, null);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, null, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode root = new TreeNode(1, node2, node3);
        dfs(root);
        levelIterate(root);
        preIterate(root);
        midIterate(root);
    }


    /**
     * 深度优先遍历
     * Depth-First Search
     * 递归
     * 前序遍历:根左右
     * 中序遍历:左根右
     * 后序遍历:左右根
     *
     * @param root 根节点
     */
    private static void dfs(TreeNode root) {
        if (null == root) {
            return;
        }
        // 前序遍历,第一次成为栈顶即打印:System.out.println(root.value);
        System.out.println(root.value);
        dfs(root.left);
        // 中序遍历,第二次成为栈顶即打印:System.out.println(root.value);
        dfs(root.right);
        // 后序遍历,第三次成为栈顶即打印:System.out.println(root.value);
    }

    /**
     * 层序遍历
     *
     * @param root 根节点
     */
    private static void levelIterate(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        levelIterate(root, 1, list);
        System.out.println(Arrays.toString(list.toArray()));
    }

    /**
     * 广度优先遍历-层序遍历.
     * 很重要:广度优先一定得知道当前层级,即数组下标
     * 1.根节点的层级(数组下标)为 1,(若当前节点下标为n)其左节点下标为 2n,右节点为 2n+1
     * 2.从上到下,从左往右,每个节点都对应一个数组下标,数组下标是 1,2,3,4...,没有子节点的可以看成左右节点都为 null
     * 假设当前节点的数组下标为n,在本次方法调用中当前节点的下标是入参传进来的是确定的n.例如从根节点开始,根节点的数组下标是 1,
     * 所以当前节点的左节点就为 2n,当前节点的右节点就为 2n+1
     * 把所有节点的下标放入list 对应的下标处,最终遍历list
     *
     * @param root 当前节点
     * @param n    当前节点下标
     * @param list list
     */
    private static void levelIterate(TreeNode root, int n, List<Integer> list) {
        if (null == root) {
            return;
        }
        // 防止数组越界,先扩容
        int len = list.size();
        if (len <= n) {
            for (int i = 0; i <= n - len; i++) {
                list.add(len + i, null);
            }
        }

        // 注意这里是 set ,指定下标赋值
        list.set(n, root.value);
        levelIterate(root.left, 2 * n, list);
        levelIterate(root.right, 2 * n + 1, list);
    }

    /**
     * 深度优先-栈实现
     * 迭代算法,用额外的数据结构，来模拟递归栈
     * 前序遍历:根左右
     * 利用栈先进后出的特性,先让根节点入栈,通过遍历这个栈来达到迭代遍历的目的
     * 在迭代中取出栈顶元素,栈顶元素就相当于当前小范围内的根节点,
     * 再依次将右节点,左节点入栈,注意入栈是先右再左,这样出栈才会先左再右
     *
     * @param root root
     */
    private static void preIterate(TreeNode root) {
        if (null != root) {
            // 遍历这个栈来迭代遍历
            Stack<TreeNode> stack = new Stack<>();
            // 根节点入栈
            stack.push(root);
            while (!stack.isEmpty()) {
                // 取出栈顶元素,栈顶元素就是小范围内的根节点了
                root = stack.pop();
                if (null != root) {
                    System.out.println(root.value);
                    // 先右
                    stack.push(root.right);
                    // 再左
                    stack.push(root.left);
                }
            }
        }
    }

    /**
     * 迭代实现中序遍历:左根右
     *
     * @param root root
     */
    private static void midIterate(TreeNode root) {
        // 遍历这个栈来迭代遍历
        Stack<TreeNode> stack = new Stack<>();
        // null != root 为了确保一开始能进入循环,在做循环遍历时不要把 root.left 直接作为节点放入,肯定会有空值的情况
        while (!stack.isEmpty() || null != root) {
            // 最左侧节点已经入栈
            if (null != root) {
                // 该 if 里的操作就是先将左侧节点依次入栈,不管 left 是否为空,为空就说明到最左侧的最后一个节点了
                stack.push(root);
                root = root.left;
            } else {
                // null==root 就说明该节点左侧所有都已经遍历完了,此时栈顶就是最左侧元素,拿出来打印就好了
                // 打印完之后就是遍历栈顶元素的右侧节点,做法就是 root=root.right
                // 如果从root=root.right进来的 root 还是 null 就说明该root右侧也全部遍历过了,接着从栈顶取好了
                root = stack.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }

}
