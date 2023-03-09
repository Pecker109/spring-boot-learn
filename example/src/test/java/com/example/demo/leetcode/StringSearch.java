package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 字符串搜索:给定两个字符串 A和B，判断 B 是否在 A 中存在，存在返回A 中的数组下标，不存在返回-1
 * 例如: 目标字符串A：ABCABDABCD,模板字符串B：ABCD 返回 6
 */
public class StringSearch {
    public static void main(String[] args) {
        String target = "ABCABDABCDA";
        String pattern = "ABCDA";
        System.out.println(bruteforce(target, pattern));
        System.out.println(rk(target, pattern));
        System.out.println(bm(target, pattern));
        //kmpSearch(target, pattern);
    }

    /**
     * 暴力算法
     *
     * @param target   目标字符串
     * @param template 模版字符串
     * @return 目标中的下标
     */
    public static int bruteforce(String target, String template) {
        // 目标字符串的长度
        int tLength = target.length();
        // 模式串的长度
        int pLength = template.length();

        // 循环目标字符串
        for (int i = 0; i <= tLength - pLength; i++) {
            // 循环模式串
            int j = 0;
            while ((j < pLength) && (target.charAt(i + j) == template.charAt(j))) {
                j++;
            }
            if (j == pLength) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 每个字符计算一个 hash 值,累加 hash 值,移动的过程中前减后加
     *
     * @param target  target
     * @param pattern pattern
     * @return return
     */
    public static int rk(String target, String pattern) {
        // 目标字符串的长度
        int tLength = target.length();
        // 模式串的长度
        int pLength = pattern.length();
        // 模式串的长度hash
        int pHash = 0;
        // 模式串的长度hash
        int tHash = 0;
        for (char c : pattern.toCharArray()) {
            pHash += hash(c);
        }
        // 循环目标字符串
        for (int i = 0; i <= tLength; i++) {
            // 加尾
            tHash += hash(target.charAt(i));
            if (i < pLength - 1) {
                continue;
            }
            // 当前指针
            int index = i + 1 - pLength;
            if (tHash == pHash) {
                // 遍历比较
                for (int j = 0; j < pLength; j++) {
                    if (target.charAt(index + j) != pattern.charAt(j)) {
                        break;
                    } else if (j == pLength - 1) {
                        return index;
                    }
                }
            }
            // 去头
            tHash -= hash(target.charAt(index));
        }
        return -1;
    }

    private static int hash(char c) {
        return c;
    }

    /**
     * bm 算法:从选中串中从后往前比较,根据坏字符和好后缀计算出模式串要往后移动的位数
     *
     * @param target  target
     * @param pattern pattern
     * @return return
     */
    private static int bm(String target, String pattern) {
        // 坏字符规则：当target中的某个字符跟pattern的某个字符不匹配时，称target中的这个失配字符为坏字符，此时模式串需要向右移动，移动的位数 = 在模式串中出现坏字符的索引下标 - 坏字符在模式串中最右出现的位置。此外，如果”坏字符”不包含在模式串之中，则最右出现位置为-1；
        // 好后缀：当字符失配时，后移位数 = 好后缀在模式串中的位置 - 好后缀在模式串上一次出现的位置，且如果好后缀在模式串中没有再次出现，则为-1。

        if (pattern == null || pattern.length() == 0) {
            return 0;
        }

        // 构建模式串的数组表（哈希表）:字符和字符在模式串中出现的索引关联
        // 要把每个字符出现最右的索引存下
        int[] suffix = generateSuffix(pattern);

        // 主串下次移动位数
        int skip;
        // 遍历主串
        for (int i = 0; i <= target.length() - pattern.length(); i += skip) {
            skip = 0;
            // 模式串从前往后遍历
            for (int j = pattern.length() - 1; j >= 0; j--) {
                if (pattern.charAt(j) != target.charAt(i + j)) {
                    // 例子: 主串 ABC 模式串: CBA ,后缀字符C和A失配,目的要直接把模式串 C 和主串 C 对齐
                    // 假设主串中出现的坏字符为c,坏字符法求移动的距离就是,将模式串中最右边出现c的位置和主串中这个坏字符 c 对齐.
                    // 不相等时表示出现坏字符, 在模式串中出现坏字符的位置就是 j
                    char bad = target.charAt(i + j);
                    // 移动的位数 = 在模式串中出现坏字符的索引下标 - 坏字符在模式串中最右出现的位置
                    skip = j - suffix[bad];
                    skip = Math.max(skip, 1);
                    break;
                }
            }
            // 跳出上层的 for 循环之后不需要skip=0 说明已经匹配了,skip 大于说明还没有匹配到,但是主串已经匹配完了
            if (skip == 0) {
                return i;
            }
        }
        return -1;
    }


    private static int[] generateSuffix(String pattern) {
        int max = 128;
        int[] suffix = new int[max];
        // 初始化数组
        for (int i = 0; i < max; i++) {
            suffix[i] = -1;
        }
        // 最右的索引下标会替换前面的下标,因为在匹配坏字符在模式串中的索引时只需要该字符最右位置的下标即可
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            suffix[c] = i;
        }

        return suffix;
    }


    static void kmpSearch(String target, String pattern) {
        /*
        前缀：字符串 S 递归去掉最后一位的集合（去尾）。比如：ABCD 前缀集合：ABC AB A
	    后缀：字符串 S 递归去掉第一位的集合（掐尾）。比如：ABCD 前缀集合：BCD CD D
	    PMT 值：前缀和后缀集合中，最长元素的长度，比如：ABCAB
		前缀集合：ABCA ABC AB A
		后缀集合：BCAB CAB AB B
        PMT 值 2，即 'AB'
         */

        int i = kmp(target.toCharArray(), pattern.toCharArray());
        System.out.println(i);
        //System.out.println(target.indexOf(pattern));
    }

    static int kmp(char[] str, char[] pattern) {
        int[] next = getNext(pattern);

        int i = 0, j = 0;
        while (i < str.length && j < pattern.length) {
            if (j == -1 || pattern[i] == pattern[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == pattern.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    static int[] getNext(char[] pattern) {
        int[] next = new int[pattern.length];
        Arrays.fill(next, -1);

        // i 原模式串下标,j 复制串下标
        int j = -1;

        for (int i = 1; i < pattern.length; ++i) {
            if (j == -1 || pattern[i] == pattern[j]) {
                ++j;
            }
            next[i] = j;
        }


        //while (i < pattern.length - 1) {
        //    if (j == -1) {
        //        i++;
        //        j++;
        //    } else if (pattern[i] == pattern[j]) {
        //        i++;
        //        j++;
        //        next[i] = j;
        //    } else {
        //        j = next[j];
        //    }
        //}

        return next;
    }
}
