package com.example.demo.leetcode;

/**
 * 跳跃游戏
 * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
 */
public class CanJump55 {

    public static void main(String[] args) {

    }


    public boolean solution(int[] nums) {

        /*
        设想一下，对于数组中的任意一个位置 y，我们如何判断它是否可以到达？
        根据题目的描述，只要存在一个位置 x，它本身可以到达，
        并且它跳跃的最大长度为 x+nums[x]，这个值大于等于 y，
        即 x+nums[x]≥y，那么位置 y 也可以到达。
        核心思路:当前下标能到达的最远位置比 n-1 大就可以到达
         */

        int n = nums.length;
        // 当前下标能到达的最远位置
        int mostLength = 0;
        // 遍历每一个下标,计算能到达的最远距离
        for (int i = 0; i < n; i++) {
            // 首先当前下标得可达
            if (i <= mostLength) {
                mostLength = Math.max(mostLength, i + nums[i]);
                if (mostLength >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
