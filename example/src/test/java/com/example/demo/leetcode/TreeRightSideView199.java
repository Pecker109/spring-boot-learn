package com.example.demo.leetcode;

import java.util.*;

/**
 * 二叉树右侧视图
 */
public class TreeRightSideView199 {
    /*
     * 示例:
     *              3
     *          5       1
     *       6     2   0   8
     *            7 4
     * 右侧视图为:3,1,8,4
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

        List<Integer> view1 = solution(root);
        System.out.println(view1);

        List<Integer> view2 = solution2(root);
        System.out.println(view2);


    }

    /**
     * 广度优先遍历,队列实现
     * 关键点:
     * 1.当前节点的层级
     * 2.按照广度优先将节点放入队列
     * 3.遍历完后需要确定二叉树的深度.由层级确定
     * 时间复杂度 : O(n)。 每个节点最多进队列一次，出队列一次，因此广度优先搜索的复杂度为线性。
     * 空间复杂度 : O(n)。每个节点最多进队列一次，所以队列长度最大不不超过 n，所以这里的空间代价为 O(n)
     *
     * @param root 根节点
     * @return return
     */
    public static List<Integer> solution(TreeNode root) {
        Map<Integer, Integer> depthMap = new HashMap<Integer, Integer>();
        int depth = -1;

        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<Integer> levelQueue = new LinkedList<Integer>();
        nodeQueue.add(root);
        // 根节点层级定义为从 0 开始
        levelQueue.add(0);

        while (!nodeQueue.isEmpty()) {

            TreeNode node = nodeQueue.remove();
            int level = levelQueue.remove();

            if (node != null) {
                // 直接数据即为广度优先算法
                System.out.println(node.value);
                // 维护二叉树的最大深度
                depth = Math.max(depth, level);
                // 由于每一层最后一个访问到的节点才是我们要的答案，因此不断更新对应深度的信息即可
                depthMap.put(level, node.value);

                nodeQueue.add(node.left);
                nodeQueue.add(node.right);
                // 每次循环都会移除头节点层级,所以左右节点的层级都要放到层级队列中
                levelQueue.add(level + 1);
                levelQueue.add(level + 1);
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for (int i = 0; i <= depth; i++) {
            rightView.add(depthMap.get(i));
        }

        return rightView;
    }

    public static List<Integer> solution2(TreeNode root) {
        Map<Integer, Integer> depthMap = new HashMap<>();
        levelIterate(root, 1, depthMap);

        List<Integer> rightView = new ArrayList<Integer>();
        int level = 1;
        boolean flag = true;
        while (flag) {
            if (null == depthMap.get(level)) {
                flag = false;
            } else {
                rightView.add(depthMap.get(level));
            }
            level++;
        }

        return rightView;
    }

    /**
     * 广度优先遍历
     *
     * @param root     当前节点
     * @param level    当前层级
     * @param depthMap key-当前层级,value-最右节点值
     */
    public static void levelIterate(TreeNode root, Integer level, Map<Integer, Integer> depthMap) {

        if (root == null) {
            return;
        }
        depthMap.put(level, root.value);
        levelIterate(root.left, level + 1, depthMap);
        levelIterate(root.right, level + 1, depthMap);

    }


}

