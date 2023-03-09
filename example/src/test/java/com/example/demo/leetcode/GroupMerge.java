package com.example.demo.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 省份数量:同一个组内所有节点都直接或间接相连,没有孤立节点,互不相连的节点为不同的组.
 * 给定一个二维数组矩阵isConnected,isConnected[i][j]=1表示节点 i 和 j 相连,为 0 为不相连
 * 求该矩阵中有多少个分组.
 * 例如{{1,1,0},{1,1,0},{0,0,1}}表示1 和 2 为一组,3 为一组
 */
public class GroupMerge {
    public static void main(String[] args) {
        int[][] isConnected1 = new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};// 2
        int[][] isConnected2 = new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};// 3
        System.out.println(mergeAndFind(isConnected1));
        System.out.println(mergeAndFind(isConnected2));
    }


    /**
     * 二维数组矩阵,毋庸置疑也是每个节点都需要遍历的.记住二维数组也是:层层遍历,先节点,再关系
     * 第一维是按各个节点进行遍历,第二维遍历的是当前节点和其他节点的关联关系.
     * 本题中能够想到用打标记的方式也很关键.打标记,打标记,打标记.
     * 已经遍历过的无需遍历.已经遍历过的无需遍历.已经遍历过的无需遍历.
     * 把已经访问过的节点打上标记,一来后续不用再进行遍历了,二来未打标记的节点就是一个独立的组了.
     * 深度优先遍历和广度优先遍历都是打标记的过程.
     *
     * @param isConnected 节点关联关系矩阵
     * @return groups
     */
    private static int getGroups1(int[][] isConnected) {
        int groups = 0;
        // isConnected长度就是总共的节点数
        int nodes = isConnected.length;
        // 在遍历的过程中只要被访问过的节点就说明该节点已经和之前访问过的节点直接或者间接相连了
        boolean[] visited = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            // 节点没有被访问过说明该节点为一个新的组了
            if (!visited[i]) {
                dfs(i, nodes, visited, isConnected);
                groups++;
            }
        }
        return groups;
    }

    /**
     * dfs 深度优先: 所谓深度优先就是直接利用其已知的关联关系进行往下递归,
     * 比如本题中利用着 i 节点和 j 节点进行关联,有关联了就接着对 j 进行递归遍历了
     * 再比如递归遍历二叉树:利用根节点对子节点进行递归遍历.
     * 执行一次该方法会把所有与节点 i 直接或间接关联的节点全部标记出来.
     * 时间复杂度：O(n^2) 空间复杂度：O(n)
     *
     * @param i           当前节点 i
     * @param nodes       总节点数
     * @param visited     标记着该节点是否别访问过
     * @param isConnected isConnected
     */
    private static void dfs(int i, int nodes, boolean[] visited, int[][] isConnected) {
        // 从前往后遍历,j 节点无需再从 0 处节点开始遍历,因为前面的节点已经遍历过了
        for (int j = i + 1; j < nodes; j++) {
            // isConnected[i][j] == 1表示和 i 节点关联才进行对 j 节点进行遍历
            // !visited[j] 表示:节点 j 没有被访问过才需要遍历,访问过就无需进行再遍历了
            if (isConnected[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(j, nodes, visited, isConnected);
            }
        }
    }


    private static int getGroups2(int[][] isConnected) {
        int groups = 0;
        // isConnected长度就是总共的节点数
        int nodes = isConnected.length;
        // 在遍历的过程中只要被访问过的节点就说明该节点已经和之前访问过的节点直接或者间接相连了
        boolean[] visited = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            // 节点没有被访问过说明该节点为一个新的组了
            if (!visited[i]) {
                bfs(i, nodes, visited, isConnected);
                groups++;
            }
        }
        return groups;
    }

    /**
     * 广度优先遍历:广度优先就是层序遍历，不通过已知的节点关联关系进行遍历的话就只能通过另外一个集合（Queue 或者 stack）来保存原节点的关联关联了
     * 同样的遵循一个原则：已经被标记过就说明该节点已经被关联过，被关联过就说明已经是属于某一个组了，无需再进行遍历了
     * 执行一次该方法会把所有与节点 i 直接或间接关联的节点全部标记出来.
     * 广度优先的精髓：
     * -- 先找节点 1 直接关联的节点，入队 Queue
     * -- 再找节点 1 间接关联的节点，遍历 Queue
     *
     * @param i           节点 i
     * @param nodes       总节点数
     * @param visited     标记着该节点是否别访问过
     * @param isConnected isConnected
     */
    private static void bfs(int i, int nodes, boolean[] visited, int[][] isConnected) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        // while遍历的是节点 i外面一层一层的数据,第一层是与 i 直接关联的节点,第二层是与 i 间接(间接一层)关联的节点,依次类推
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            // for循环的是其他节点与节点 i 的关联关系
            for (int j = i + 1; j < nodes; j++) {
                if (isConnected[i][j] == 1 && !visited[j]) {
                    visited[j] = true;
                    queue.offer(j);
                }
            }
        }
    }

    /**
     * 所有直接关联或者间接关联的节点都指向一个共同的节点（逻辑上直接关联的根节点），比如 1 关联 2,2关联 3，那么就让 3 直接关联 1.
     *
     * @param isConnected isConnected
     * @return return
     */
    private static int mergeAndFind(int[][] isConnected) {
        int groups = 0;
        int nodes = isConnected.length;
        // 记录各个节点的逻辑根节点
        int[] heads = new int[nodes];
        // 记录各个节点所在组的层高
        int[] levels = new int[nodes];
        //初始：
        for (int i = 0; i < nodes; i++) {
            // 默认逻辑根节点都是本身节点
            heads[i] = i;
            // 默认层高都是 1
            levels[i] = 1;
        }

        // 按每个节点进行遍历
        for (int i = 0; i < nodes; i++) {
            // 每个节点关联几点进行遍历
            for (int j = i; j < nodes; j++) {
                // 相邻就进行合并
                if (isConnected[i][j] == 1) {
                    merge(i, j, heads, levels);
                }
            }
        }

        // 遍历完后所有当前节点号等于逻辑根节点的为独立的一组
        for (int i = 0; i < nodes; i++) {
            if (heads[i] == i) {
                groups++;
            }
        }
        return groups;
    }

    /**
     * 都指向共同的根节点，将层数低的往层数高的合并
     *
     * @param i      节点 i
     * @param j      节点 j
     * @param heads  逻辑根节点数组
     * @param levels 层高数组
     */
    private static void merge(int i, int j, int[] heads, int[] levels) {
        // i 的逻辑根结点
        int x = find(i, heads);
        // j 的逻辑根结点
        int y = find(j, heads);
        if (x == y) {
            return;
        }
        //合并:低节点往高节点合并
        if (levels[x] <= levels[y]) {
            //i的根节点为 x,此时 x 的根节点应该指向 y
            heads[x] = y;
        } else {
            heads[j] = x;
        }
        if (levels[x] == levels[y]) {
            levels[x]++;
            levels[y]++;
        }
    }

    /**
     * 查找该节点逻辑上直接关联的根节点，递归查找，查找的同时并标记出逻辑根节点
     *
     * @param i     节点 i
     * @param heads heads
     * @return 逻辑跟节点
     */
    private static int find(int i, int[] heads) {
        if (heads[i] == i) {
            return i;
        }
        heads[i] = find(heads[i], heads);
        return heads[i];
    }

}
