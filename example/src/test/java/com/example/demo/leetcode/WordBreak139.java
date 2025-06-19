package com.example.demo.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单词拆分:
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。
 * 如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
 * 不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 * 示例 1：
 * - 输入: s = "leetcode", wordDict = ["leet", "code"]
 * - 输出: true
 * - 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 * 示例 2：
 * - 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * - 输出: false
 */
public class WordBreak139 {

    public static void main(String[] args) {
        String s = "leetcode";
        String[] wordDict = {"leet", "code"};
        System.out.println(solution1(s, Arrays.asList(wordDict)));
    }

    /**
     * @param word s
     * @param dict dict
     * @return boolean
     */
    public static boolean solution(String word, List<String> dict) {


        Set<String> wordDictSet = new HashSet<>(dict);
        // dp[i] 表示字符串 s 前 i 个字符组成的字符串 s[0..i−1] 是否能被空格拆分成若干个字典中出现的单词。
        boolean[] dp = new boolean[word.length() + 1];
        dp[0] = true;
        for (int len = 1; len <= word.length(); len++) {
            for (int start = 0; start < len; start++) {
                // 0~start可以拼接,并且,start~len可以拼接,那么 0~len 就可以拼接
                if (dp[start] && wordDictSet.contains(word.substring(start, len))) {
                    dp[len] = true;
                    break;
                }
            }
        }
        return dp[word.length()];
    }

    /**
     * 动态规划
     *
     * @param word word
     * @param dict dict
     * @return return
     */
    public static boolean solution1(String word, List<String> dict) {

        Set<String> set = new HashSet<>(dict);

        // 1.历史状态
        boolean[] dp = new boolean[word.length() + 1];
        // 2.状态初始值
        dp[0] = true;
        // 3.状态变更方程
        // 4.递归顺序
        for (int len = 1; len <= word.length(); len++) {
            for (int start = 0; start < len; start++) {
                String substring = word.substring(start, len);
                if (dp[start] && set.contains(substring)) {
                    dp[len] = true;
                    break;
                }
            }
        }
        // 5.返回结果
        return dp[word.length()];
    }
}
