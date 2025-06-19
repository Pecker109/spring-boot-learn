package com.example.demo.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 二叉树的最近公共祖先:
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 */
public class LowestCommonAncestor236 {

    /*
     * 示例:
     *              3
     *          5       1
     *       6     2   0   8
     *            7 4
     * 节点 5 和节点 1 的最近公共祖先是节点 3 。
     * 节点 6 和节点 4 的最近公共祖先是节点 5 。
     * 节点 5 和节点 4 的最近公共祖先是节点 5 。根据定义最近公共祖先节点可以为节点本身。
     */

    public static void main(String[] args) {
        TreeNode node9 = new TreeNode(4, null, null);
        TreeNode node8 = new TreeNode(7, null, null);
        TreeNode node7 = new TreeNode(8, null, null);
        TreeNode node6 = new TreeNode(0, null, null);
        TreeNode node5 = new TreeNode(2, node8, node9);
        TreeNode node4 = new TreeNode(6, null, null);
        TreeNode node3 = new TreeNode(1, node6, node7);
        TreeNode node2 = new TreeNode(5, node4, node5);
        TreeNode root = new TreeNode(3, node2, node3);

        TreeNode common = solution(root, node2, node3);
        System.out.println(common.value);
    }

    /**
     * key-每个节点,value-当前节点的父节点
     */
    static Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
    static Set<Integer> visited = new HashSet<Integer>();

    public static TreeNode solution(TreeNode root, TreeNode p, TreeNode q) {
        // 深度优先遍历,将每个节点与之对应的父节点存储到 Map 中
        dfs(root);
        // 将 p 节点的所有父节点存储到 Set 中
        while (p != null) {
            visited.add(p.value);
            p = parent.get(p.value);
        }
        // 从 q 节点开始遍历,由于是一步步往上找父节点,所以 q 节点的父节点也存在于 Set集合中,则当前节点 p 为最近父节点
        while (q != null) {
            if (visited.contains(q.value)) {
                return q;
            }
            // 往上遍历 q 节点的父节点
            q = parent.get(q.value);
        }
        return null;
    }

    /**
     * 深度优先遍历,将每个节点与之对应的父节点存储下来
     * Depth-First Search
     *
     * @param root 当前节点
     */
    public static void dfs(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.value, root);
            dfs(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.value, root);
            dfs(root.right);
        }
    }


}
