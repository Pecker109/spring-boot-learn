package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 给定一个整型乱序数组,求数组中三个数最大乘积
 * 例如:[-1,-2,4,5,6] 返回 120
 */
public class MaxProduct {


    public static void main(String[] args) {
        /*
         全正数情况: 最大的 3 个数乘积
         全负数情况: 最大的 3 个数乘积
         有正有负: Max(最大的3个数乘积,最小的两个数*最大的数)
         综合上述可能性,只要计算 Max(最大的3个数乘积,最小的两个数*最大的数) 就可以了
         这道题的关键在于找出题目中关联的数量关系,即"最大的3个数"和"最小的两个数"
         */
        int[] nums = new int[]{-1, -2, 4, 5, 6};
        System.out.println(maxProductBySort(nums));
        System.out.println(maxProductLinearScan(nums));

    }

    // 算法 1:排序.时间复杂度 O(NlogN),取决于排序算法
    public static int maxProductBySort(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        return Math.max(nums[len - 1] * nums[len - 2] * nums[len - 3], nums[0] * nums[1] * nums[len - 1]);
    }

    // 算法 2:线性扫描.时间复杂度O(N)
    public static int maxProductLinearScan(int[] nums) {
        // 思路: 一次扫描,扫描出"最大的3个数"和"最小的两个数"
        // max0:最大,max1:第二大,max2:第三大
        int max0 = Integer.MIN_VALUE, max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        // min0:最小,min1:第二小
        int min0 = Integer.MAX_VALUE, min1 = Integer.MAX_VALUE;
        for (int x : nums) {
            // 求最小的两个数
            if (x < min0) {
                min1 = min0;
                min0 = x;
            } else if (x < min1) {
                min1 = x;
            }
            // 求最大的3个数
            if (x > max0) {
                max2 = max1;
                max1 = max0;
                max0 = x;
            } else if (x > max1) {
                max2 = max1;
                max1 = x;
            } else if (x > max2) {
                max2 = x;
            }
        }

        return Math.max(max0 * max1 * max2, max0 * min0 * min1);
    }
}
