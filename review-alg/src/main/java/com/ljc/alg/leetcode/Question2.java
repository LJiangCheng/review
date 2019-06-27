package com.ljc.alg.leetcode;


import java.math.BigInteger;

public class Question2 {

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */


    //错误解 错误原因：不允许使用内置的BigInteger等库，而使用Long是不够的的
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        String l1s = getListStr(l1);
        String l2s = getListStr(l2);
        BigInteger sum = new BigInteger(l1s).add(new BigInteger(l2s));
        char[] chars = String.valueOf(sum).toCharArray();
        ListNode listNode = null;
        for (char cha : chars) {
            //从最后一个节点往前建
            if (listNode == null) {
                listNode = new ListNode(Integer.parseInt(new String(new char[]{cha})));
            } else {
                ListNode preview = new ListNode(Integer.parseInt(new String(new char[]{cha})));
                preview.next = listNode;
                listNode = preview;
            }
        }
        return listNode;
    }

    private String getListStr(ListNode l1) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(l1.val);
        while (l1.next != null) {
            l1 = l1.next;
            sb1.append(l1.val);
        }
        sb1.reverse();
        return sb1.toString();
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
