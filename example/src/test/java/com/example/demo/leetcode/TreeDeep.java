package com.example.demo.leetcode;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉树的最小深度
 */
public class TreeDeep {
    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, node7, null);
        TreeNode node5 = new TreeNode(5, null, null);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, node6, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode root = new TreeNode(1, node2, node3);
        System.out.println(minDeep1(root));
        System.out.println(minDeep2(root));
    }

    /*
        1
      /   \
     2     3
    / \   /
   4   5 6
        /
       7
最小深度: 1-2-4 或 1-2-5,深度为 3
最大深度: 1-3-6-7 深度为 4
     */

    /**
     * 深度优先: 以叶子节点为计算长度的起点.
     * 时间复杂度 O(n),空间复杂度 O(logN)取决于树的深度
     *
     * @param root 根节点
     * @return 当前根节点最小深度
     */
    private static int minDeep1(TreeNode root) {
        int min = Integer.MAX_VALUE;
        if (null == root) {
            return 0;
        }
        // 叶子节点,返回叶子结点本身的深度1
        if (null == root.left && null == root.right) {
            return 1;
        }
        // 递归求左节点的最小深度
        if (null != root.left) {
            min = Math.min(min, minDeep1(root.left));
        }
        // 递归求右节点的最小深度
        if (null != root.right) {
            min = Math.min(min, minDeep1(root.right));
        }
        // 左右节点的深度都计算完成,返回子节点最小的深度+本身节点,也就是 min+1
        return min + 1;
    }

    /**
     * 广度优先: 以叶子节点为找到深度的终点.找到叶子节点就说明是最小深度了.
     * 时间复杂度 O(n),空间复杂度 O(N)取决于 Queue
     * 根节点标为 1,节点中要定义一个深度的变量
     * 依次把左右节点放入 Queue 中,遍历 Queue 进行节点的查找
     *
     * @param root
     * @return
     */
    private static int minDeep2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        root.deep = 1;
        queue.add(root);

        // 遍历 Queue
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // 由于是广度遍历,所以只要遇到左右节点都是 null 的就说明当前节点就是最小深度的节点了.这一点很关键
            // 目的就是找叶子节点 ,只要不是环形一定会找到,找到叶子节点就找到了最小深度了
            if (null == node.left && null == node.right) {
                return node.deep;
            }
            //有左节点: 左节点深度+1 放入队列,接着循环 Queue 往下找
            if (null != node.left) {
                node.left.deep = node.deep + 1;
                queue.add(node.left);
            }
            if (null != node.right) {
                node.right.deep = node.deep + 1;
                queue.add(node.right);
            }
        }
        // 只要不是环形,就不会从这里返回
        return 0;
    }

}
