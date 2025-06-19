package com.example.demo.leetcode;

/**
 * 验证是否为回文序列
 * 给定一字符串,验证是否为回文序列
 * 示例:
 * abcba:true
 * abc:false
 */
public class Palindrome18 {


    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        System.out.println(solution(s));

    }

    public static boolean solution(String s) {
        StringBuilder sgood = new StringBuilder();
        int length = s.length();
        // 只保留数字和单词,去除标点符号
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(Character.toLowerCase(ch));
            }
        }
        int n = sgood.length();
        // 双指针:头指针,尾指针
        int left = 0, right = n - 1;
        while (left < right) {
            if (Character.toLowerCase(sgood.charAt(left)) != Character.toLowerCase(sgood.charAt(right))) {
                return false;
            }
            ++left;
            --right;
        }
        return true;
    }
}
