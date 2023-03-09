package com.example.demo.leetcode;

/**
 * 斐波那契数列,求第 N 位的值
 * 每一位的值等于前两位数字之和，如 0,1,1,2,3,5,8… ,第 6 位返回 8
 */
public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(cal1(6));
        System.out.println(cal2(6));
        System.out.println(calByTwoPoint(6));
    }

    /**
     * 暴力递归.时间复杂度O(2^n)
     *
     * @param n n
     * @return return
     */
    private static int cal1(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        // 每次都会把比当前节点小的节点全部再算一遍,造成重复的计算
        // 比如 cal1(5) 会把 cal(4),cal(3),cal(2),cal(1)全部算一遍,
        // cal1(4) 又会把 cal(3),cal(2),cal(1) 全部算一遍
        return cal1(n - 1) + cal1(n - 2);
    }

    /**
     * 去重递归,时间复杂度O(n),空间复杂度O(n)
     *
     * @param n n
     * @return return
     */
    private static int cal2(int n) {
        // arr 默认值都为 0
        int[] arr = new int[n + 1];
        return recurse(arr, n);
    }

    private static int recurse(int[] arr, int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        // arr[n] != 0 表示数组中已经有值,说明已经计算过了
        if (arr[n] != 0) {
            return arr[n];
        }
        arr[n] = recurse(arr, n - 1) + recurse(arr, n - 2);
        return arr[n];
    }


    /**
     * 双指针算法:从 0 开始,low =0,high = 1
     * 时间复杂度O(n),空间复杂度O(1)
     *
     * @param n n
     * @return return
     */
    private static int calByTwoPoint(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int low = 0, high = 1;
        for (int i = 2; i <= n; i++) {
            // 双指针的核心变量替换
            int sum = low + high;
            low = high;
            high = sum;
        }

        return high;
    }

}
