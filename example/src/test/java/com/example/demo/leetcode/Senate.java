package com.example.demo.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * 参议院：两个阵营，每个议员有淘汰一名议员的权利，直到只剩一方议员为止
 * 关键点在于该议员行使完权利之后需要如何记录下次轮到自己的时机。并且当前行使权利的议员最有利的选择是淘汰掉另外一方下标最小的议员
 * 队列就是一个很好的机制，一个R队列一个D队列，每次行使权利之后下标加 n 来保证不会影响对本轮比较冲突。
 */
public class Senate {

    public static void main(String[] args) {
        String senate = "RDRD";
        System.out.println(vote(senate));
    }

    private static String vote(String senate) {
        // 存放"R"下标的双端队列,使用双端队列是因为要从队首拿,再放到队尾,保证下一次循环
        Deque<Integer> rQueue = new LinkedList<>();
        Deque<Integer> dQueue = new LinkedList<>();
        // 初始化
        for (int i = 0; i < senate.length(); i++) {
            if (senate.charAt(i) == 'R') {
                rQueue.offer(i);
            } else {
                dQueue.offer(i);
            }
        }
        while (!rQueue.isEmpty() && !dQueue.isEmpty()) {
            int rIndex = rQueue.poll();
            int dIndex = Optional.ofNullable(dQueue.poll()).orElse(-1);
            if (rIndex < dIndex) {
                // 每次行使权利之后下标加 length 来保证不会影响本轮比较
                rQueue.addLast(rIndex + senate.length());
            } else {
                dQueue.addLast(dIndex + senate.length());
            }
        }
        return rQueue.isEmpty() ? "D" : "R";
    }
}
