package com.example.demo.leetcode;

/**
 * 二叉树
 */
public class TreeNode {
    // 当前节点数据
    int value;
    // 左节点
    TreeNode left;
    // 右节点
    TreeNode right;
    // 深度
    int deep;

    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
