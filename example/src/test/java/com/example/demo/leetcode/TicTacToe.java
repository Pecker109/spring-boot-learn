package com.example.demo.leetcode;

/**
 * 井字游戏:给定一个棋面,判断该棋面是否存在,x先走
 * 比如 :
 * ooo
 * xxx
 * ---
 * 该棋面不可能形成，因为3个连着的只能形成一个
 */
public class TicTacToe {
    public static void main(String[] args) {
        String[] board = new String[]{"xxx", "ooo", "   "};
        System.out.println(validBoard(board));
    }

    private static boolean validBoard(String[] board) {
        /*
        分类讨论,穷举有限的情况:
        X 赢: x-o=1
        O 赢: x-o=0
        没有人赢:x-o=1或x-o=0
         */
        int xCount = 0, oCount = 0;
        for (String row : board) {
            for (char s : row.toCharArray()) {
                if (s == 'x') {
                    xCount++;
                } else if (s == 'o') {
                    oCount++;
                }
            }
        }
        // 只有 x 和o 相等的和 x比 o 大 1 的情况
        if (xCount - oCount != 0 && xCount - oCount != 1) {
            return false;
        }

        if (canWin("xxx", board) && xCount - oCount != 1) {
            return false;
        }

        if (canWin("ooo", board) && xCount - oCount != 0) {
            return false;
        }

        return true;
    }

    private static boolean canWin(String flag, String[] board) {

        for (int i = 0; i < 3; i++) {
            // 横 3 连
            if (flag.equals(board[i])) {
                return true;
            }
            // 竖 3 连
            if (flag.equals("" + board[0].charAt(i) + board[1].charAt(i) + board[2].charAt(i))) {
                return true;
            }

        }
        // '\'斜 3 连
        if (flag.equals("" + board[0].charAt(0) + board[1].charAt(1) + board[2].charAt(2))) {
            return true;
        }
        // '/'斜 3 连
        return flag.equals("" + board[0].charAt(2) + board[1].charAt(1) + board[2].charAt(0));
    }
}
