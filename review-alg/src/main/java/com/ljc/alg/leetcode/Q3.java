package com.ljc.alg.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Q3 {

    /*给定一个字符串，找出其中不含有重复字符的 最长子串 的长度。  输入: "abcabcbb"  输入: "pwwkew"*/
    private int test1(String s) {
        //"abcabcbb" "pwwkew"
        char[] chars = s.toCharArray();
        int subLen = 0;  //最大长度
        Map<Character,Integer> uniMap = new HashMap<>();  //重复判断并下标记录
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (uniMap.containsKey(c)) {
                //记录最大长度并清理容器
                subLen = recordLen(uniMap, chars, subLen, c, i);
            } else {
                uniMap.put(c, i);
            }
        }
        //最后的子序列
        if (subLen < uniMap.size()) {
            subLen = uniMap.size();
        }
        return subLen;
    }

    private int recordLen(Map<Character, Integer> uniMap, char[] chars, int subLen, char c, int i) {
        //当前长度判断
        int size = uniMap.size();
        if (subLen < size) {
            subLen = size;
        }
        //容器清理
        Integer preIndex = uniMap.get(c);
        int start = i - size;
        for(int j=start;j<=preIndex;j++) {
            uniMap.remove(chars[j]);
        }
        //投入当前字符
        uniMap.put(c, i);
        return subLen;
    }

    @Test
    public void test() {
        System.out.println(test1("adcv"));
    }

}
