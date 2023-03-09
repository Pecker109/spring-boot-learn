package com.example.demo.leetcode;

/**
 * 打家劫舍:给定一个数组，找出任意两个下标都不连续值求和的最大值
 * 例如:[1,2,3,1] nums[0]+nums[1] 此时最大为 4
 */
public class Rob {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        System.out.println(recursion(nums, nums.length - 1));
        System.out.println(getMaxByDp(nums));
        System.out.println(getMaxByNoDp(nums));

        // 首尾相连:首位只能任选其一
        System.out.println(Math.max(getMaxByNoDp(nums, 0, nums.length - 2),
                getMaxByNoDp(nums, 1, nums.length - 1)));
        // 连接方式是个二叉树,父节点和子节点任选其一
        getMaxByTree();
    }

    /**
     * 求 nums 数组中 0-index 位置的不连续下标的最优解
     *
     * @param nums  nums
     * @param index 下标
     * @return 最大值
     */
    private static int recursion(int[] nums, int index) {
        /*
         * 递归 3 要素:
         * 1.递归的目的:求 nums 数组中 0-index 位置的不连续下标的最优解
         * 2.递归不等式:index 位置的最优解为:recursion[index-2]+nums[index]和recursion[index-1]的较大值
         * 3.递归不等式:index=0 返回nums[0]
         */
        if (null == nums || index < 0) {
            return 0;
        }
        if (index == 0) {
            return nums[0];
        }
        return Math.max(recursion(nums, index - 1), recursion(nums, index - 2) + nums[index]);
    }

    /**
     * 动态规划3 要素
     * 1.最优子结构：每条最优解包含子问题的最优解。
     * 2.递推公式，可以是从后往前推，或者从前往后推。
     * 3.存在重叠子问题
     *
     * @param nums nums
     * @return return
     */
    private static int getMaxByDp(int[] nums) {
        /*
         * 将 recursion()方法中的每一步结果都存下来,存到 dp 数组中
         * dp 数组：几维由递归的影响因子数决定
         * dp 数组下标 i 存的值就是 0-i 中最优解的值了,目的就是求 dp 数组
         * 怎么求 dp 数组呢？
         * 我们可以明确的知道当dp 数组下标 i =0，1时的值，那么我们求i=2时就是 dp[0]+dp[2]和 dp[1]两者的较大值了，
         * 以此类推。其实就是从前往后推计算 dp 数组。
         */
        if (null == nums) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        return dp[nums.length - 1];
    }

    /**
     * 对于 getMaxByDp()空间复杂度上的优化
     *
     * @param nums nums
     * @return return
     */
    private static int getMaxByNoDp(int[] nums) {
        /*
         * getMaxByDp() 方法中 dp[] 的计算过程中只用到了 dp[i-2]和dp[i-1],所以可以将这两个变量单拎出来
         */
        if (null == nums) {
            return 0;
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = second;
            second = Math.max(temp, first + nums[i]);
            first = temp;
        }
        return second;
    }

    /**
     * nums 数组的首位和末尾只能任选其一
     * 那么就将原数组分成两种情况:不包含头的和不包含尾的,按原来的思想再分别计算
     *
     * @param nums  nums
     * @param start start
     * @param end   end
     * @return return
     */
    private static int getMaxByNoDp(int[] nums, int start, int end) {

        if (null == nums) {
            return 0;
        }
        int first = nums[start];
        int second = Math.max(first, nums[start + 1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = second;
            second = Math.max(temp, first + nums[i]);
            first = temp;
        }
        return second;
    }

    private static void getMaxByTree() {
        TreeNode node5 = new TreeNode(1, null, null);
        TreeNode node4 = new TreeNode(3, null, null);
        TreeNode node3 = new TreeNode(1, node5, null);
        TreeNode node2 = new TreeNode(2, node4, null);
        TreeNode node1 = new TreeNode(3, node2, node3);
        int[] nums = dfs(node1);
        System.out.println(Math.max(nums[0], nums[1]));
    }

    /**
     * 深度优先遍历二叉树,求当前节点选择与不选择情况下的最优解
     *
     * @param root 当前节点
     * @return 只有两个值, int[]{选当前节点的最优解,不选当前情况的最优解}
     */
    private static int[] dfs(TreeNode root) {
        /*
         * 递归 3 要素:
         * 1.目的:求当前节点选择和不选择的最优解 int[]{选当前节点的最优解,不选当前情况的最优解}
         * 2.递归等式:分别计算选择当前节点和不选当前节点的情况下+该情形下对应的子节点选择情况的解
         * 3.null 节点
         */
        if (root == null) {
            return new int[]{0, 0};
        }
        // 左节点的选与不选最优值
        int[] left = dfs(root.left);
        // 右节点的选与不选最优值
        int[] right = dfs(root.right);
        // 选当前节点的最优解:当前节点+左右节点不选的情况
        int select = root.value + left[1] + right[1];
        // 不选当前节点的最优解:左节点上选与不选的最优值+右节点上选与不选的最优值
        int notSelect = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{select, notSelect};
    }

}
