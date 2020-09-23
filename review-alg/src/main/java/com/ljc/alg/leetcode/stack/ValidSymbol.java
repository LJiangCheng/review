package com.ljc.alg.leetcode.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。指正确的数学顺序
 * 注意空字符串可被认为是有效字符串。
 * 如 "([)]" false "{[()]}" true
 */
public class ValidSymbol {

    public static void main(String[] args) {
        System.out.println(isValid("([)]"));
    }

    public static boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }
        //成对性可以用Map来维护
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');
        //使用LinkedList作为栈
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (!pairs.containsKey(c)) {
                stack.push(c);
            } else if (stack.isEmpty() || pairs.get(c) != stack.pop()) {
                return false;
            }
        }
        return true;
    }
}












