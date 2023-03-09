package com.example.demo.leetcode;

/**
 * 排列硬币:总共 n 枚硬币,第 k 行刚好有 k 枚硬币,求可形成完整阶梯的总行数
 * 例如: n = 4 返回 2,第一行 1 枚,第二行 2 枚,第三行不完整
 */
public class ArrangeCoin {
    public static void main(String[] args) {
        System.out.println(cal1(7));
        System.out.println(cal2(7));
        System.out.println(binary(7));
    }

    /**
     * 累加法,需要的总数 total 和给定的 n 做比较
     *
     * @param n 总硬币数
     * @return return
     */
    private static int cal1(int n) {
        int x = 0, total = 0;
        while (total < n) {
            x++;
            total += x;
            if (total > n) {
                x--;
            }
        }
        return x;
    }

    /**
     * 递减法,剩余的总数 和 下一行需要的硬币数作比较
     *
     * @param n 总硬币数
     * @return return
     */
    private static int cal2(int n) {
        for (int i = 0; i < n; i++) {
            n = n - i;
            // 剩余的硬币数 n, i 第 i 行需要 i 枚硬币
            if (n <= i) {
                return i;
            }
        }
        return n;
    }

    // 算法 2: 二分查找
    private static int binary(int n) {
        /*
          x 为完整的行数, n 枚硬币,假设 n 枚硬币就是返回 n(n即x) 行,当然我们知道 n>=x
          所以问题就变成了求第 x 行需要的总硬币数和总硬币数 n 的问题了
          第 x 行总共需要的硬币数:即 1+2+3+4+...+x=(x + x*x)/2
         */

        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            // 第 mid 行所需要的总硬币数
            int total = (mid + mid * mid) / 2;
            if (total < n) {
                low = mid + 1;
            } else if (total > n) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return high;
    }
    // 算法 3: 牛顿迭代

}
