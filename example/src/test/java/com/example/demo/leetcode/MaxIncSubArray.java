package com.example.demo.leetcode;

/**
 * 在无序数组中,求最大递增子数组长度
 * 例如:[1,2,3,2,3,4,3,4,5,6,7] 最大递增子数组为[3,4,5,6,7],长度为 5
 */
public class MaxIncSubArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 2, 3, 4, 3, 4, 5, 6, 7};
        System.out.println(maxLen(nums));
    }

    /**
     * 贪心算法：局部最优求解，不影响最终求解
     *
     * @param nums nums
     * @return return
     */
    private static int maxLen(int[] nums) {
        int max = Integer.MIN_VALUE;
        int start = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                start = i;
            }
            max = Math.max(max, i - start + 1);
        }
        return max;
    }
}
