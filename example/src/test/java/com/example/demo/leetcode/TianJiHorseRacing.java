package com.example.demo.leetcode;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 田忌赛马:给定两个大小相等的数组 A 和 B，A 相对于 B 的优势可以用满足 A[i] > B[i] 的索引 i 的数目来描述。
 * 返回 A 的任意排列，使其相对于 B 的优势最大化。
 * 例如:田忌数组 A{9,11,13,14,17,19},齐威王数组 B{10,12,16,16,20,21}
 * 返回 A{11,13,17,19,9,,14}
 */
public class TianJiHorseRacing {
    public static void main(String[] args) {
        int[] A = new int[]{9, 11, 13, 14, 17, 19};
        int[] B = new int[]{10, 12, 16, 16, 20, 21};
        System.out.println(Arrays.toString(horseRacing(A, B)));
    }

    private static int[] horseRacing(int[] aNums, int[] bNums) {
        /*
         * 先对 AB 数组排序
         * 找一个代价最小的去匹配B中的，比B大，在A中又是最小的 ,小于 B 的放入垃圾数组中,
         */
        // 排序
        int[] sortedA = aNums.clone();
        Arrays.sort(sortedA);//
        int[] sortedB = bNums.clone();
        Arrays.sort(sortedB);//避免比较时，A每次都要重头遍历

        // 方便后续根据原始的 B 返回对应排序后的 A
        Map<Integer, LinkedList<Integer>> bMap = new HashMap<>();
        for (int num : bNums) {
            bMap.put(num, new LinkedList<>());
        }
        // 垃圾数组,比较完 AB 之后,垃圾数组里的数据可以随意的排了,因为都是小于 B 的
        Deque<Integer> remaining = new LinkedList<>();
        int index = 0;
        for (int a : aNums) {
            int b = sortedB[index];
            if (a > b) {
                bMap.get(b).add(a);
                // a > b 指针 index 才需要往下移
                index++;
            } else {
                remaining.add(a);
            }
        }

        int[] result = new int[aNums.length];
        // 按照 B 原来的顺序返回排序后的 A
        for (int i = 0; i < bNums.length; i++) {
            int b = bNums[i];
            if (bMap.get(b).size() > 0) {
                result[i] = bMap.get(b).removeLast();
            } else {
                result[i] = remaining.removeLast();
            }
        }
        return result;
    }
}
