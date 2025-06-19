package com.example.demo.leetcode;

/**
 * 香槟塔:第一层 1 个杯子，第二层 2 个，依次类推
 * 求倒入 n 杯，求第 x 行，第y 列倒入了多少百分比
 */
public class ChampagneTower {

    public static void main(String[] args) {
        System.out.println(indexCa(5, 3, 3));
        //System.out.println(indexCa(6, 2, 2));
    }

    /**
     * 求给定下标的含水量
     *
     * @param n 倒入 n 杯水
     * @param x 第 x 行
     * @param y 第 y 列
     * @return 第 x 行 y 列有多少水
     */
    private static double indexCa(int n, int x, int y) {
        x--;
        y--;
        // 整个塔各个点的容量
        double[][] ca = new double[10][10];
        // 第一层的容量肯定为 n
        ca[0][0] = n;
        // 执行完这个双重 for 循环之后所有的容量就都已经分配过了
        for (int i = 0; i < x; i++) {
            for (int j = 0; j <= i; j++) {
                //上层分配过来的流量减去本身需要的 1
                double remain = ca[i][j] - 1;
                // 剩余的大于 0 才会往下分配
                if (remain > 0) {
                    // 本题的关键在于找出题目中给出的规律:
                    // 1.上层的会把减去本身需要的 1,平均分配给下一行的[i+1][j]和[i+1][j+1]
                    ca[i + 1][j] += remain / 2;
                    ca[i + 1][j + 1] += remain / 2;
                }
            }
        }
        return Math.min(1, ca[x][y]);
    }


    private static double indexCa1(int n, int x, int y) {

        x--;
        y--;

        double[][] tower = new double[10][10];
        tower[0][0] = n;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double remain = tower[i][j] - 1;
                if (remain > 0) {
                    tower[i + 1][j] = remain / 2;
                    tower[i + 1][j + 1] = remain / 2;
                }
            }
        }

        return Math.min(1, tower[x][y]);
    }
}
