package com.example.demo.leetcode;


/**
 * 子回文序列个数:
 * 给定一字符串,返回子回文序列个数
 * 示例1:
 * - 输入：s = "abc"
 * - 输出：3
 * - 解释：三个回文子串: "a", "b", "c"
 * 示例 2：
 * - 输入：s = "aaa"
 * - 输出：6
 * - 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 */
public class PalindromeSubCount20 {
    public static void main(String[] args) {
        String s = "aaa";
        System.out.println(solution2(s));
    }


    /**
     * 中心拓展
     *
     * @param s s
     * @return return
     */
    public static int solution2(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            // 子回文序列为奇数情况:中心节点从 i 位置开始
            int l = i, r = i;
            // 左边界,右边界,依次比较是否相等
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                ans++;
                --l;
                ++r;
            }

            // 子回文序列为偶数情况:中心节点从 i+1 位置开始
            l = i;
            r = i + 1;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                ans++;
                --l;
                ++r;
            }
        }
        return ans;
    }


}