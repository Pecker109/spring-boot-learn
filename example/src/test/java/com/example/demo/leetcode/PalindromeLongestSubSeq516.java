package com.example.demo.leetcode;

/**
 * 最长回文序列:
 * 给定一字符串,找出其中最长的回文子序列，并返回该序列的长度.
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 * 示例1:
 * - 输入：s = "bbbab"
 * - 输出：4
 * - 解释：一个可能的最长回文子序列为 "bbbb"
 * 示例 2：
 * - 输入：s = "cbbd"
 * - 输出：2
 * - 解释：一个可能的最长回文子序列为 "bb"
 */
public class PalindromeLongestSubSeq516 {

    public static void main(String[] args) {
        String s = "bbbab";
        System.out.println(solution2(s));
    }

    public static int solution2(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        // 固定长度,滑动窗口: bbbab
        for (int len = 2; len <= n; len++) {
            // l左边界,r右边界
            for (int l = 0; l <= n - len; l++) {
                int r = l + len - 1;
                if (s.charAt(l) == s.charAt(r)) {
                    dp[l][r] = dp[l + 1][r - 1] + 2;
                } else {
                    dp[l][r] = Math.max(dp[l + 1][r], dp[l][r - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 动态规划
     * 对于一个子序列而言，如果它是回文子序列，并且长度大于 2，那么将它首尾的两个字符去除之后，它仍然是个回文子序列
     *
     * @param s s
     * @return return
     */
    public static int solution(String s) {
        int n = s.length();
        // 1.回溯状态
        // dp[i][j] 表示 s[i..j] 的最长回文子序列长度
        int[][] dp = new int[n][n];
        // 2.状态初值
        // 单个字符是长度为 1 的回文子序列
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
            /*
            初始值为:
            [1, 0, 0, 0, 0]
            [0, 1, 0, 0, 0]
            [0, 0, 1, 0, 0]
            [0, 0, 0, 1, 0]
            [0, 0, 0, 0, 1]
             */
        }
        // 3.状态转移方程
        // 4.状态转移顺序
        // bbbab
        // 从长度为 2 的子串开始，逐步计算更长的子串
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1; // 子串的结束位置
                if (s.charAt(i) == s.charAt(j)) {
                    // 对于一个子序列而言，如果它是回文子序列，并且长度大于 2，那么将它首尾的两个字符去除之后，它仍然是个回文子序列
                    dp[i][j] = dp[i + 1][j - 1] + 2; // 状态转移方程 1
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]); // 状态转移方程 2
                }
            }
        }
        // 5.返回结果
        return dp[0][n - 1]; // 返回整个字符串的最长回文子序列长度
        /*
        最后 dp 值为:
        [1, 2, 3, 3, 4]
        [0, 1, 2, 2, 3]
        [0, 0, 1, 1, 3]
        [0, 0, 0, 1, 1]
        [0, 0, 0, 0, 1]
         */
    }


}
