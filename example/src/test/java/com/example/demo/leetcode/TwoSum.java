package com.example.demo.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和,给定一个整型数组和目标值 target,返回数组中两数之和等于 target 的数组下标
 * 例如:[1,2,3,4,5,6] target=10,返回 3,5
 */
public class TwoSum {


    public static void main(String[] args) {
        int nums[] = new int[]{1, 2, 3, 4, 5, 6};
        int target = 10;
        System.out.println(Arrays.toString(bf(nums, target)));
        System.out.println(Arrays.toString(mapTarget(nums, target)));
        System.out.println(Arrays.toString(binarySearch(nums, target)));
        System.out.println(Arrays.toString(twoPoint(nums, target)));
    }

    // 无序数组:
    // 1.暴力算法:双重 for 循环,时间复杂度O(n*n),空间复杂度 O(1)
    private static int[] bf(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0};
    }

    // 2.标记算法: 求 target - nums[i] 是否在 map 中,时间复杂度O(n),空间复杂度 O(n)
    private static int[] mapTarget(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (map.containsKey(target - x)) {
                return new int[]{map.get(target - x), i};
            }
            map.put(x, i);
        }
        return new int[]{0};
    }

    // 有序数组,定位下标 就要联想到二分查找
    // 3.有序数组:二分查找,时间复杂度O(N*logN),空间复杂度 O(1)
    private static int[] binarySearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int x = target - nums[i];
            int low = i + 1, high = nums.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (nums[mid] < x) {
                    low = mid + 1;
                } else if (nums[mid] > x) {
                    high = mid - 1;
                } else {
                    return new int[]{i, mid};
                }
            }
        }
        return new int[]{0};
    }

    // 4.有序数组:双指针,时间复杂度O(n),空间复杂度 O(1)
    // 左指针: low ,右指针: high.
    // nums[low] + nums[high] < target ==> low + 1
    // nums[low] + nums[high] > target ==> high -1
    private static int[] twoPoint(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            if (nums[low] + nums[high] < target) {
                low++;
            } else if (nums[low] + nums[high] > target) {
                high--;
            } else {
                return new int[]{low, high};
            }
        }
        return new int[]{0};
    }

}
