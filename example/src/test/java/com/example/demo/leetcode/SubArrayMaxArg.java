package com.example.demo.leetcode;

/**
 * 求子数组中最大平均值
 * 例如:输入[1,12,-5,-6,50,3] ,k=4 最大平均数=(12-5-6+50)/4=12.75
 */
public class SubArrayMaxArg {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 12, -5, -6, 50, 3};
        System.out.println(getMaxArg(nums, 4));
    }

    /**
     * 双指针:滑动窗口
     *
     * @param nums nums
     * @param k    窗口间隔
     * @return return
     */
    private static double getMaxArg(int[] nums, int k) {
        // 滑动指针
        int p1 = 0, p2, max = 0;
        for (int i = 0; i < nums.length; i++) {
            p2 = i;
            if (p2 < k) {
                max = max + nums[i];
            } else {
                // p1++ : 先赋值再加加
                max = Math.max(max, max - nums[p1++] + nums[i]);
            }
        }

        return 1.0 * max / k;
    }
}
