package com.example.demo.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * 合法的出栈序列
 * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，
 * 只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * 示例 2：
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 */
public class ValidateStackSequences946 {

    public static void main(String[] args) {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {3, 2, 5, 4, 1};
        System.out.println(solution(pushed, popped));
    }

    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        /*
         定义一个栈,模拟 popped 入栈出栈顺序,栈中还有元素说明不是合法的出栈序列.
         那么程序中要考虑的就是入栈和出栈的时机了
         双指针:指针 1 指向要入栈元素,指针 2 指向出栈元素:
             指针 1 和指针 2 不相等时,入栈指针 1 移动
             指针 1 和指针 2 相等时,出栈指针 2 移动
         */
        // 双端队列,操作栈顶元素相当于入栈出栈,
        // 这里一定要用双端队列,不能用 Queue,因为入栈,判断栈顶元素,出栈3 个动作要操作的都是栈顶元素,双端队列可以实现这样的功能
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int len = pushed.length;
        // 双指针:指针 1 指向要入栈元素,指针 2 指向出栈元素
        for (int i = 0, j = 0; i < len; i++) {
            // 放队列头相当于放栈顶(入栈)
            stack.push(pushed[i]);
            // 栈顶元素等于要出栈元素
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                // 移除栈顶元素(出栈)
                stack.pop();
                // 只有相等时指针 2 才往前移动
                j++;
            }
        }
        return stack.isEmpty();
    }

    public static boolean solution(int[] pushed, int[] popped) {
        // 双端队列也可以实现 Stack 的逻辑
        Stack<Integer> stack = new Stack<>();
        for (int i = 0, j = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }
}
