package com.ljc.alg.leetcode.dailyIssue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * q.617
 * 合并二叉树
 * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
 * <p>
 * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
 */
public class MergeTrees {

    //深度优先遍历：纵向为先，使用递归，需回溯(入栈、出栈)
    public TreeNode depthMerge(TreeNode t1, TreeNode t2) {
        //合并节点：先把t1或t2为空的情况排除，再处理t1和t2都不为空的情况
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        //新建节点，合并左右子树
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.left = depthMerge(t1.left, t2.left);
        node.right = depthMerge(t1.right, t2.right);
        return node;
    }

    //广度优先遍历：横向层序遍历，需要借助队列迭代。
    //队列的作用：1.记录后续需要处理的节点（任务队列） 2.保持顺序
    @SuppressWarnings("all")
    public TreeNode breadthMerge(TreeNode t1, TreeNode t2) {
        //先把t1或t2为空的情况排除，再处理t1和t2都不为空的情况
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        //都不为空：合并
        TreeNode node = new TreeNode(t1.val + t2.val);
        //将三棵树(新/t1/t2)的根节点分别放入三个队列
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();
        queue.offer(node);
        q1.offer(t1);
        q2.offer(t2);
        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode n = queue.poll(), n1 = q1.poll(), n2 = q2.poll();
            TreeNode left1 = n1.left, left2 = n2.left, right1 = n1.right, right2 = n2.right;
            //分别处理左右节点
            if (left1 != null || left2 != null) {
                if (left1 != null && left2 != null) {
                    //如果同时不为空，意味着需要合并左子树
                    TreeNode left = new TreeNode(left1.val + left2.val);
                    n.left = left;
                    //将三个左节点放入各自的队列，依据入队顺序，处理完上层节点之后就会从左往右处理下层节点。右节点处理方式同左节点
                    queue.offer(left);
                    q1.offer(left1);
                    q2.offer(left2);
                } else if (left1 == null) {
                    //如果left1为空，把left2设为新的左节点即可，则以left2为根节点的子树已经连接到新树上，不必再放入队列进行下一轮处理。反之亦然
                    n.left = left2;
                } else {
                    n.left = left1;
                }
            }
            if (right1 != null || right2 != null) {
                if (right1 != null && right2 != null) {
                    TreeNode right = new TreeNode(right1.val + right2.val);
                    n.right = right;
                    queue.offer(right);
                    q1.offer(right1);
                    q2.offer(right2);
                } else if (right1 == null) {
                    n.right = right2;
                } else {
                    n.right = right1;
                }
            }
        }
        return node;
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
