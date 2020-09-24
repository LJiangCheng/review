package com.ljc.alg.leetcode.dailyIssue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 501 二叉搜索数众数查找
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * <p>
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 * 1
 * \
 * 2
 * /
 * 2
 * 返回[2].
 * <p>
 * 提示：如果众数超过1个，不需考虑输出顺序
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 */
public class BSTModes {

    public int[] findMode(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        markMode(root, map);
        List<Map.Entry<Integer, Integer>> collect = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList());
        Collections.reverse(collect);
        List<Integer> list = new ArrayList<>();
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : collect) {
            Integer count = entry.getValue();
            if (count >= max) {
                max = count;
                list.add(entry.getKey());
            } else {
                break;
            }
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private void markMode(TreeNode root, Map<Integer, Integer> map) {
        if (root != null) {
            int val = root.val;
            if (map.containsKey(val)) {
                map.put(val, map.get(val) + 1);
            } else {
                map.put(val, 1);
            }
            markMode(root.left, map);
            markMode(root.right, map);
        }

    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

}
