package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 求数组中心下标
 * 中心下标指该位置左侧所有元素相加的和等于右侧所有元素相加的和
 * 例如[1,7,3,6,5,6] 返回 3
 */
public class ArrayCenterIndex {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 7, 3, 6, 5, 6};
        System.out.println(pivotIndex(nums));
    }

    public static int pivotIndex(int[] nums) {
        // 双指针
        // 指针 1:sum ,每次循环减去 num[i]
        // 指针 2:total ,每次循环要先加上 num[i]
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        // sum + nums[i] == total - nums[i] 返回 i
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            if (total == sum) {
                return i;
            }
            // 搞不清什么临界值找一个简单的示例比如:[7,2,4,3]
            // 所以说计算 total == sum 时是都包含 num[i] 的节点的,不相等才会 sum - nums[i]
            sum = sum - nums[i];
        }
        // [(1,7,3),6,(5,6)]
        return -1;
    }
}
