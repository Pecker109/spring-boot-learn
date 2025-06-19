package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 合并两个有序数组,合并后还是有序
 * 例如:nums1=[1,3,5,7,9,0,0,0,0] nums2=[2,4,6,8] 返回 nums1=[1,2,3,4,5,6,7,8,9]
 */
public class MergeSortArray {

    public static void main(String[] args) {
        // 注意数组中的元素个数m和数组长度len 不是一个概念,比如数组 nums1 ,元素个数为 5,len=9
        int[] nums1 = new int[]{3, 5, 7, 9, 0, 0, 0, 0, 0};
        int[] nums2 = new int[]{1, 2, 4, 6, 8};

        //System.out.println(Arrays.toString(mergeBySort(nums1, 4, nums2, nums2.length)));
        System.out.println(Arrays.toString(mergeByTwoPoint1(nums1, 4, nums2, nums2.length)));
        //System.out.println(Arrays.toString(mergeByTwoPoint2(nums1, 4, nums2, nums2.length)));
    }

    /**
     * 时间复杂度取决于排序算法,排序算法时间复杂度 O(nlogN)
     *
     * @param nums1 原数组,长度为 m+n,元素个数为 m
     * @param m     nums1中元素个数
     * @param nums2 nums2
     * @param n     nums2中元素个数
     * @return 合并后的有序的 nums1
     */
    private static int[] mergeBySort(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
        return nums1;
    }


    /**
     * 双指针算法,p1 指向 nums1,p2 指向 nums2,往新数组中合并
     * 时间复杂度 O(n),空间复杂度O(n)
     *
     * @param nums1 原数组,长度为 m+n,元素个数为 m
     * @param m     nums1中元素个数
     * @param nums2 nums2
     * @param n     nums2中元素个数
     * @return 合并后的有序的 nums1
     */
    private static int[] mergeByTwoPoint1(int[] nums1, int m, int[] nums2, int n) {
        // 创建一个新的数组,往新数组中合并,一个指针指向 new_arr,一个指针指向 nums 或 nums2
        int p1 = 0, p2 = 0, p = 0;
        int[] new_arr = new int[m + n];

        // 只要 p1 > m 或者 p2 > n 就说明有数组已经遍历到头了,那么另外一个数组只需要把剩下的元素整体拷贝过去就行了
        while (p1 < m && p2 < n) {
            // 注意:由于 i++ 操作是先赋值再加加,所以可采用以下形式
            // 也就是说如果 (new_arr[p1] <= nums2[p2])=true ,先进行操作 nums1[p] = new_arr[p1],再进行 p1=p1+1,p=p+1
            new_arr[p++] = (nums1[p1] <= nums2[p2]) ? nums1[p1++] : nums2[p2++];
        }

        // new_arr 中元素已经移完了,nums2 中还有值
        if (p1 >= m) {
            System.arraycopy(nums2, p2, new_arr, p, m + n - p);
        }
        // nums2 中元素已经移完了,nums1 中还有值
        if (p2 >= n) {
            System.arraycopy(nums1, p1, new_arr, p, m + n - p);
        }
        return new_arr;
    }


    /**
     * 双指针算法,从 nums1 最后一个元素开始插入,这样就减少了一个数组的创建
     * 时间复杂度 O(n),空间复杂度O(1)
     *
     * @param nums1 原数组,长度为 m+n,元素个数为 m
     * @param m     nums1中元素个数
     * @param nums2 nums2
     * @param n     nums2中元素个数
     * @return 合并后的有序的 nums1
     */
    private static int[] mergeByTwoPoint2(int[] nums1, int m, int[] nums2, int n) {

        // p1 指向nums1数组的 m-1 位,p2 指向nums2数组的n-1位,p=nums1.length-1
        int p1 = m - 1, p2 = n - 1, p = m + n - 1;

        while (p1 >= 0 && p2 >= 0) {
            nums1[p--] = (nums1[p1] >= nums2[p2]) ? nums1[p1--] : nums2[p2--];
        }
        // 如果 p2<0 了,nums1[p1] 前面的的节点就不用再移动了,无需操作了
        // 如果 p1<0,把 nums[p2] 前面的节点全部移动到 nums[1]
        if (p1 < 0) {
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
        }
        return nums1;
    }

}
