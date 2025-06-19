package com.example.demo.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TemplateFunction {
    /**
     * 二分查找模版
     *
     * @param nums   已知有序数据
     * @param target 目标值
     * @return 结果值
     */
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            // 中间的: int mid = low + (high - low) / 2
            mid = (left + right) >> 1;
            if (nums[mid] == target) return mid; //找到了，返回
            if (nums[mid] > target) right = mid - 1; //大了，去左边找
            if (nums[mid] < target) left = mid + 1; //小了，去右边找
        }
        return -1;
    }

    /**
     * 动态规划（记忆化搜索）
     * 将问题分解成若干相互重叠的子问题
     * 动态规划是典型的"空间换时间"算法,创建新的空间记录已经求解的子问题的结果,后续遇到此子问题，直接使用结果,所以别名也叫"记忆化搜索"
     * <1>. 动态规划的核心思想
     * - 01.分治思想： 将原问题分解为若干子问题，递归求解子问题。
     * - 02.-保存子问题的解：通过表格（通常是数组）保存子问题的解，避免重复计算。
     * - 03.自底向上或自顶向下：
     * -- 自底向上：从最小的子问题开始，逐步求解更大的子问题。
     * -- 自顶向下：从原问题开始，递归求解子问题，通常结合记忆化（Memoization）。
     * <2>. 动态规划的适用场景
     * - 01.最优化问题：求最大值、最小值、最长、最短等。
     * - 02.重叠子问题：子问题之间存在重叠，即多个子问题共享相同的子子问题。
     * - 03.最优子结构：问题的最优解可以通过子问题的最优解推导出来。
     * <3>.动态规划的基本步骤
     * - 01.定义状态：
     * -- 明确问题的状态表示，通常用一个或多个变量表示。
     * -- 例如：dp[i] 表示前 i 个元素的最优解。
     * - 02.初始化状态：
     * -- 确定初始状态的值，通常是最小的子问题的解。
     * -- 例如：dp[0] = 0, dp[1] = 1。
     * - 03.确定状态转移方程：
     * -- 描述状态之间的关系，即如何从子问题的解推导出当前问题的解。
     * -- 例如：dp[i] = dp[i - 1] + dp[i - 2]。
     * - 04.计算顺序：
     * -- 确定状态的计算顺序，通常是从小到大（自底向上）或从大到小（自顶向下）。
     * - 05.返回结果：
     * -- 根据状态的定义，返回最终的结果。
     * <p>
     * 求斐波那契数列就是最好的例子: 当n=0时候，为0，n=1时为1，n>=2时，就是前两个数字之和，比如:0 1 1 2 3 5 8 13
     *
     * @param n 已知条件
     * @return 结果值
     */
    public static int fibonacci(int n) {
        if (n == 0) return 0;
        // 01.定义状态:用来保存从0到n的n个斐波那契数
        int[] fib = new int[n + 1];
        // 02.初始化状态
        fib[0] = 0; //f(0)=0
        fib[1] = 1; //f(1)=1
        // 04.计算顺序:从小到大
        for (int i = 2; i <= n; i++) {
            // 03.确定状态转移方程
            fib[i] = fib[i - 1] + fib[i - 2]; //f(x) = f(x-1) + f(x-2)
        }
        // 05.返回结果
        return fib[n];
    }

    /**
     * 贪心算法
     * 1. 贪心算法的基本思想
     * 贪心算法的每一步都做出一个 局部最优选择，并希望通过一系列局部最优选择达到全局最优解。它的特点是：
     * a.贪心选择性质：每一步都选择当前最优的解，不考虑未来的影响。
     * b.无后效性：当前的选择不会影响后续的选择。
     * <p>
     * 2. 贪心算法的适用条件
     * 贪心算法适用于满足以下条件的问题：
     * a.最优子结构：问题的最优解包含子问题的最优解。
     * b.贪心选择性质：通过局部最优选择能够达到全局最优解。
     * <p>
     * 3. 贪心算法的基本步骤
     * a.问题分解：将问题分解为若干个子问题。
     * b.局部最优选择：对每个子问题，选择一个局部最优解。
     * c.合并解：将局部最优解合并为全局解。
     * <p>
     * 4. 贪心算法的局限性
     * 贪心算法并不总是能得到全局最优解，只有在问题满足贪心选择性质时才适用。例如：
     * a.背包问题：贪心算法不能解决 0-1 背包问题，但可以解决分数背包问题。
     * b.最短路径问题：贪心算法不能解决带负权边的最短路径问题。
     * <p>
     * 5. 贪心算法与动态规划的区别
     * 贪心算法：每一步都选择局部最优解，不回溯。
     * 动态规划：通过保存子问题的解，避免重复计算，通常用于解决具有重叠子问题的问题。
     * <p>
     * 贪心算法经典问题:找零钱
     *
     * @param coins  可用的硬币面值数组
     * @param amount 需要凑出的金额
     * @return 返回凑出金额的硬币列表，如果无法凑出则返回 null
     */
    public static List<Integer> coinChange(List<Integer> coins, int amount) {
        // 将硬币面值从大到小排序
        coins.sort(Collections.reverseOrder());

        List<Integer> result = new ArrayList<>();
        for (int coin : coins) {
            while (amount >= coin) {
                result.add(coin);  // 将当前硬币加入结果列表
                amount -= coin;    // 减去当前硬币的面值
            }
        }

        // 如果剩余金额为 0，返回结果列表；否则返回 null
        return amount == 0 ? result : null;
    }


    /**
     * 递归
     *
     * @param level level表示递归深度
     * @param param param表示此深度对应的参数
     */
    public void recursion(int level, int param) {
        // MAX 表示业务上具体终止的条件
        int MAX = Integer.MAX_VALUE;
        //检测终止条件
        if (level > MAX) return; //这里可以返回结果
        //处理当前层逻辑
        handleLogic(level, param); //这里可以改变param
        //深入到下一层
        recursion(level + 1, param);
    }

    public void handleLogic(int level, int param) {
    }

    /**
     * 递归求斐波那契
     *
     * @param n 第 n 项
     * @return 数列和
     */
    public int fibRecursion(int n) {
        //检测终止条件
        if (n < 0) throw new IllegalArgumentException("fuck the argument"); //参数不合法
        if (n == 0) return 0; // f(0)=0
        if (n == 1) return 1; // f(1)=1

        //这里的当前结果也就是返回值

        //深入下一层
        return fibonacci(n - 1) + fibonacci(n - 2); //否则就是f(x-1)+f(x-2)
    }

    /**
     * 二叉树的深度优先遍历-
     * Depth-First Search
     * 先序遍历,递归方式遍历
     *
     * @param root 当前节点
     */
    public void dfs(TreeNode root) {
        //检测终止条件
        if (root == null) return;
        //处理当前层逻辑
        System.out.println(root.value);
        //深入下一层
        dfs(root.left);
        dfs(root.right);
    }

    /**
     * 夹逼,双指针
     *
     * @param arr 已知条件
     * @return 结果值
     */
    public int squeeze(int[] arr) {
        //定义左右边界
        int left = 0, right = arr.length - 1, result = 0;
        //左右边界不碰撞，就不断接近
        while (left < right) {
            //处理当前区间逻辑
            result = handleLogic();
            //移动边界
            if (logic(left) < logic(right)) left++;
            else right--;
        }
        //返回结果
        return result;
    }

    public int handleLogic() {
        return 0;
    }

    public int logic(int num) {
        return 0;
    }

}
