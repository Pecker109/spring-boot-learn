package com.example.demo.leetcode;

/**
 * 给定一个无序正整数数组,两名玩家,每次只能从剩余的数组中拿头部或者尾部数据,求第一个先拿的是否存在一种必能拿到最大数总和的取法
 * 例如{1,100,3,4}就存在,只要保证每次取偶数位的数据就可以.
 * 再例如{1,100,3,4,5}不存在
 */
public class CanWin {
    public static void main(String[] args) {
        // nums 元素个数为偶数一定存在,为奇数要看数组的内容了
        // int[] nums = new int[]{1, 100, 3, 4, 5}; // false
        int[] nums = new int[]{1, 100, 3, 4}; // true
        System.out.println(canWin(nums));
        // 差值大于 0 说明有必赢策略
        System.out.println(maxDiff(nums, 0, nums.length - 1) > 0);
    }

    private static boolean canWin(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int p1Score = maxScore(nums, 0, nums.length - 1);
        // p1Score 大说明存在必赢策略
        return p1Score > sum - p1Score;
    }

    /**
     * 递归求 l 到 r 位置,最优情况下能拿到的最大分数总和
     *
     * @param nums nums
     * @param l    l
     * @param r    r
     * @return 最优情况下能拿到的最大分数总和
     */
    private static int maxScore(int[] nums, int l, int r) {
        /*
         递归 3 要素:
         1.递归函数的目的,递归要求什么值,要返回什么参数
         2.出口:极端情况的返回值
         3.范围缩减等式
         */
        /*
         本地为例,找 3 要素:
         1.目的:求最优情况的总和
         2.出口:只剩一个元素 l == r,或者只剩两个元素r - l == 1,是可以确定拿哪个值的
         3.不等式:剩余元素求最大值的递归
         */
        if (l == r) {
            return nums[l];
        }
        if (r - l == 1) {
            return Math.max(nums[r], nums[l]);
        }

        // 拿左边情况的最优情况总和
        int sLight = 0;
        // 拿右边情况的最优情况总和
        int sRight = 0;
        if (r - l > 2) {
            // 拿左侧情况的最大值:左侧数据+(另外一个人拿左边或者右边情况剩余的最小值,因为另外一个人也肯定会拿剩余情况中最大的值)
            // maxScore(nums, l + 2, r) 表示:另外一个人拿的 l+1 位置的值,那么剩余的从 l+2 到 r 中最大的数据
            // maxScore(nums, l + 1, r - 1)表示:另外一个人拿的 r 位置的值,那么剩余的从 l+1 到 r-1中最大的数据
            // Math.min 表示:另外一个人一定会从剩余情况中拿最大值,那么当前这个人一定就是另外一种较小的情况了
            sLight = nums[l] + Math.min(maxScore(nums, l + 2, r), maxScore(nums, l + 1, r - 1));
            // 右侧数据同理
            sRight = nums[r] + Math.min(maxScore(nums, l + 1, r - 1), maxScore(nums, l, r - 2));

            // 可以看到 maxScore(nums, l + 1, r - 1) 遍历了两次,因此可以省略一次
        }
        // 返回拿左边或者拿右边最优的情况
        return Math.max(sLight, sRight);
    }

    /**
     * 递归求 l 到 r 位置,拿 l 位置或者 r 位置最优情况的最大差值
     * 可以以{100,2,3}为例:
     * 拿左边的情况 sLight = nums[l] - maxDiff(nums, l + 1, r) ==> 100-(2-3)=101 或者 100 -(3-2)=99
     * 拿右边的情况 sRight = nums[r] - maxDiff(nums, l, r - 1) ==> 3-(100-2)=-95 或者 3-(2-100)=101
     *
     * @param nums nums
     * @param l    l
     * @param r    r
     * @return 差值
     */
    private static int maxDiff(int[] nums, int l, int r) {
        if (l == r) {
            return nums[l];
        }
        // 拿左边情况的最优情况总和
        int sLight = 0;
        // 拿右边情况的最优情况总和
        int sRight = 0;
        // 取左边数据,左边元素和剩余 l+1 到 r 位置最优值的差值
        sLight = nums[l] - maxDiff(nums, l + 1, r);
        // 取右边数据,剩余 l 到 r-1 位置的最大差值
        sRight = nums[r] - maxDiff(nums, l, r - 1);
        // 返回拿左边或者拿右边最优的情况
        return Math.max(sLight, sRight);
    }

}
