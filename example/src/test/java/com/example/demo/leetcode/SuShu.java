package com.example.demo.leetcode;


import java.util.Arrays;

/**
 * 给定一个整数n,求0-n 所有的素数的总数
 * 素数:只能被 1 和它本身整除.例如:1,2,3,5,7
 */
public class SuShu {
    public static void main(String[] args) {
        suShu(100);
    }

    /**
     * 思路: 合数,从当前 i 开始标记出以 i 为乘数的所有合数
     *
     * @param n n
     */
    private static void suShu(int n) {
        boolean[] all = new boolean[n];// false 为素数
        Arrays.fill(all, false);
        int count = 0;
        // 从 2 开始
        for (int i = 2; i < n; i++) {
            // 当 i 为素数时
            if (!all[i]) {
                count++;
                System.out.println(i);
                // 从 i*i 开始,把 all[] 中以 i 为乘数的都标记为非素数(也叫合数)
                for (int j = i; i * j < n; j++) {
                    all[i * j] = true;
                }
            }
        }
        System.out.println(count);
    }

    // 不能被 2,3,5,7 整除和平方根不是整数
    private static void suShu1(int n) {
        //
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (i == 2
                    || i == 3
                    || i == 5
                    || i == 7) {
                count++;
                System.out.println(i);
                continue;
            }
            int a = i % 2;
            int b, c = 0, d = 0;
            b = i % 3;
            if (i > 5) {
                c = i % 5;
            }
            if (i > 7) {
                d = i % 7;
            }
            double sq = Math.sqrt(i);
            boolean e = ((sq - Math.floor(sq)) == 0);
            if (a != 0
                    && b != 0
                    && c != 0
                    && d != 0
                    && !e
            ) {
                count++;
                System.out.println(i);
            }

        }
        System.out.println(count);
    }
}
