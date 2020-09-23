package com.ljc.alg.tree;

import java.util.TreeMap;

public class TreeMapAnalyze {

    /**
     * Every node is either red or black.  每一个节点必须是红色或者黑色
     * The root is black.  根节点是红色
     * Every leaf (NIL) is black.  叶子节点必须是黑色 ----优化条件？
     * If a node is red, then both its children are black.  红色节点的子节点必须是黑色
     * For each node, all simple paths from the node to descendant leaves contain the same number of black nodes. 每一条路径上的黑色节点数目一致
     * 最后两点保证 红黑树的高度log（N+1）
     */
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();

    }

}
