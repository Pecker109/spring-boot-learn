package com.example.demo.leetcode;

/**
 * 柠檬水找零,柠檬水售价 5,顾客排队买,一次一杯,顾客付的面额为 5,10,15.能正确找零返回 true,否则 false
 */
public class LemonCharge {
    public static void main(String[] args) {
        int[] bills = new int[]{5, 10, 20, 5, 20};
        System.out.println(charge(bills));
    }

    /**
     * 贪心算法,局部最优求解,全局最优
     *
     * @param bills 排队付钱的顺序
     * @return true-可正确找零
     */
    private static boolean charge(int[] bills) {
        // 初始零钱为 0,20 的面额对于找零没作用,所以不需要记录
        int five = 0, ten = 0;
        for (int bill : bills) {
            // 5元直接入账
            if (bill == 5) {
                five++;
            }
            // 10 元找零 5 元,10 元入账
            if (bill == 10) {
                if (five > 0) {
                    five--;
                    ten++;
                } else {
                    return false;
                }
            }
            // 20:方案 1:优先 10+5 方案 2:5+5+5
            if (bill == 20) {
                // 优先找 10 元,达到局部最优
                if (ten > 0 & five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
