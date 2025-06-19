package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 用最少数量的箭引爆气球
 */
public class FindMinArrowShots452 {

    public static void main(String[] args) {
        int[][] points1 = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        System.out.println(solution(points1));
    }

    public static int solution(int[][] points) {
        // 贪心
        int n = points.length;
        if (n == 0) return 0;

        // 按照右端点从小到大排序
        Arrays.sort(points, (a, b) -> Long.compare(a[1], b[1]));
        int result = 1;
        // 每只箭射在当前遍历气球的右端点
        // 第一支箭直接射出
        int position = points[0][1];
        for (int i = 1; i < n; i++) {
            // 当前遍历气球的左端点小于上个箭头位置说明上个箭头可以射到当前气球,否则射不到,需要增加射箭次数,新加的射箭位置为气球右端点
            if (points[i][0] <= position) {
                // 该区间能被当前箭right穿过
                continue;
            }
            result++; // 箭数加1
            position = points[i][1]; // 继续射出箭,箭从当前气球的右端点射出
        }
        return result;
    }

}
