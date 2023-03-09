package com.example.demo.leetcode;

/**
 * 有序数组去重,并返回新数组长度,不能产生新数组
 * [0,1,2,2,3,3,4]  返回 5
 */
public class SortArrayDuplicates {

    public static void main(String[] args) {
        int length = removeDuplicates(new int[]{0, 1, 2, 2, 3, 3, 4});
        System.out.println(length);
    }

    private static int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        // 双指针:快慢指针,快指针遍历原有的数组长度,慢指针其实是新的数组长度
        // 慢指针
        int slow = 0;
        // 快指针
        for (int j = 1; j < nums.length; j++) {
            if (nums[slow] != nums[j]) {
                // 先++再赋值给nums[slow]
                slow++;
                // 以 slow 为下标的数组就是个互不重复的数组了 nums[0,1,2,3,4]
                nums[slow] = nums[j];
            }
        }
        // slow 是数组下标,返回的长度要 +1
        return slow + 1;
    }
}
