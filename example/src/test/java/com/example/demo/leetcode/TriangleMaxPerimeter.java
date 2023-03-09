package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 给定一个无序数组,求数组中能组成三角形的最大周长.能组成三角形的条件:a+b>c and c>a and c>b
 */
public class TriangleMaxPerimeter {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 4, 7, 6, 3, 4};
        System.out.println(getMax(nums));
    }

    private static int getMax(int[] nums) {
        // 先按升序排序,排序之后必然满足 c>a and c>b
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            // 再比较a+b>c
            if (nums[i - 1] + nums[i - 2] > nums[i]) {
                return nums[i] + nums[i - 1] + nums[i - 2];
            }
        }
        return 0;
    }

}
