package com.example.demo.leetcode;


/**
 * @author Pecker
 * @Description 判断二叉树是否为平衡二叉树
 * @since 2021-04-24
 */
public class BalancedBinaryTree {
    /**
     * 平衡二叉树的定义是：
     * 二叉树的每个节点的左右子树的高度差的绝对值不超过 1，则二叉树是平衡二叉树。
     * 根据定义，一棵二叉树是平衡二叉树，当且仅当其所有子树也都是平衡二叉树，
     * 因此可以使用递归的方式判断二叉树是不是平衡二叉树，递归的顺序可以是自顶向下或者自底向上。
     */

    /*
     * 解法: 自上向下的递归
     * 1.递归求层高
     * 2.递归判断每个子节点是否是平衡二叉树
     */

    /**
     * 判断 root 节点是否是平衡二叉树
     * 需要满足条件:
     * 1.当前 root 节点的左右节点层高绝对值相差小于 1
     * 2.子节点的也是平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return Math.abs(height(root.left) - height(root.right)) <= 1
                    && isBalanced(root.left)
                    && isBalanced(root.right);
        }
    }

    /**
     * 求层高
     *
     * @param root 求传入节点 root 的层高
     * @return 层高
     */
    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }


    public class TreeNode {
        // 左节点
        TreeNode left;
        // 右节点
        TreeNode right;
        // 当前节点数据
        byte[] data;
    }

}
