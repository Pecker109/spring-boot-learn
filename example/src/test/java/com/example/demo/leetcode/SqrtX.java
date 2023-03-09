package com.example.demo.leetcode;

/**
 * 在不使用 sqrt(x)函数的情况下,求 X 的平方根的整数部分
 * 例如: 4 返回 2; 5 返回 2;
 */
public class SqrtX {

    public static void main(String[] args) {
        System.out.println(binarySearch(5));
        System.out.println(binarySearch(4));
    }


    /**
     * 二分查找
     * 二分查找关键 3 个变量: 左节点 left ,中间节点 middle,右节点 right
     *
     * @param x x
     * @return return
     */
    private static int binarySearch(int x) {
        int left = 0, right = x;
        int index = -1;
        while (left <= right) {
            // mid 的值定义很关键
            int mid = left + (right - left) / 2;
            // 一般都是要用 mid 值来进行操作判断和目标值之间的大小关系
            // 本题目中目标值是 x,mid 的操作是 mid * mid
            if (mid * mid <= x) {
                index = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }
}
