package com.ljc.alg.tree;

import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 图和树的遍历是必会的算法之一，在这里通过代码和注释将这个问题讲清楚，以便后来复习。
 * 前序遍历：对于每个子树按 根节点->左节点->右节点 的顺序输出，每个子树都先输出根节点
 * 中序遍历：按 左节点->根节点->右节点 顺序输出
 * 后序遍历：按 左节点->右节点->根节点 顺序输出
 * 以上三种同属于深度优先遍历，对节点的考察顺序是相同的，但输出顺序不同
 * 而层序遍历属于广度优先遍历
 * 层序遍历：从根节点开始往下一层一层依次遍历
 */
public class TreeIterator {

    public static void main(String[] args) {
        List<TreeNode> treeNodes = postOrderTraversal(createNode());
        for (TreeNode node : treeNodes) {
            System.out.print(node.val + " ");
        }
        System.out.println();
        List<List<TreeNode>> lists = levelOrderTraversal(createNode());
        for (List<TreeNode> list : lists) {
            for (TreeNode node : list) {
                System.out.print(node.val);
            }
            System.out.print(" ");
        }
    }

    /**
     * 最简单的：递归解法
     * 递归一般可用于实现深度优先遍历（前中后序），层序遍历一般不使用递归解法
     * 实现非常简单：不同的只有操作的时间点
     */
    public static List<TreeNode> recursionDeepTraversal(TreeNode root, List<TreeNode> list) {
        if (root != null) {
            //前序操作5 4 2 1 3 7 6
            list.add(root);
            recursionDeepTraversal(root.left, list);
            //中序1 2 3 4 5 6 7
            //list.add(root);
            recursionDeepTraversal(root.right, list);
            //后序1 3 2 4 6 7 5
            //list.add(root);
        }
        return list;
    }

    /**
     * 迭代之先序遍历常规解法
     *   初始化栈，并将根节点入栈；
     *   当栈不为空时：
     *       弹出栈顶元素 node，并将值添加到结果中；
     *       如果 node 的右子树非空，将右子树入栈；
     *       如果 node 的左子树非空，将左子树入栈；
     */
    public static List<TreeNode> preTraversal(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        //初始化栈，将根节点压栈
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode cur = stack.pop();
            if (cur != null) {
                //将出栈的节点放入结果集中
                list.add(cur);
                //右节点、左节点先后入栈，则左节点先出栈
                stack.push(cur.right);
                stack.push(cur.left);
            }
        }
        return list;
    }

    /**
     * 迭代解深度优先之前/中序遍历模板解法
     */
    public static List<TreeNode> preMiddleTraversal(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        //初始化栈
        Stack<TreeNode> stack = new Stack<>();
        //外循环将节点弹出并添加到结果集
        TreeNode cur = root;
        while (cur != null || !stack.empty()) {
            //内循环
            while (cur != null) {
                //前序遍历（中左右）：入栈前加入结果集
                list.add(cur);
                //将当前节点及所有左节点压栈，压栈顺序：当前节点、左节点、右节点
                stack.push(cur);
                cur = cur.left;
            }
            //弹出一个节点：由于内循环的存在，这个值不可能为null
            TreeNode temp = stack.pop();
            //中序遍历（左中右）：弹出后加入结果集
            //list.add(temp);
            //每弹出一个立即处理其右子树
            cur = temp.right;
        }
        return list;
    }

    /**
     * 迭代解深度优先之后序遍历
     */
    public static List<TreeNode> postOrderTraversal(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        //初始化栈
        Deque<TreeNode> stack = new LinkedList<>();
        //外循环将节点弹出并添加到结果集
        TreeNode cur = root;
        TreeNode prev = null;
        while (cur != null || !stack.isEmpty()) {
            //内循环
            while (cur != null) {
                //将当前节点及所有左节点压栈：遍历顺序左右中
                stack.push(cur);
                cur = cur.left;
            }
            //弹出一个节点
            TreeNode temp = stack.pop();
            //如果弹出的节点没有右子树或右子树已经遍历完毕，则直接将其记录到结果集；否则将它重新入栈并先处理右子树
            //判断依据：当前节点的右节点为空 或 当前节点的右节点就是上一个已经处理过的节点
            if (temp.right == null || temp.right == prev) {
                list.add(temp);
                //记录这个节点，并将当前正在处理的节点记录置空（否则会再将其所有左节点再处理依次，死循环）
                prev = temp;
                cur = null;
            } else {
                //重新入栈（每一颗存在左右子树的根节点都会入栈两次），先行处理右子树
                stack.push(temp);
                cur = temp.right;
            }
        }
        return list;
    }

    /**
     * 5 47 26 13
     * 迭代解广度优先遍历
     * 使用队列，以中、左、右的顺序先进先出
     *  初始化队列 q，并将根节点 root 加入到队列中；
     *  当队列不为空时：
     *      队列中弹出节点 node，加入到结果中；
     *      如果左子树非空，左子树加入队列；
     *      如果右子树非空，右子树加入队列；
     */
    public static List<List<TreeNode>> levelOrderTraversal(TreeNode root) {
        //将每一层放到不同的集合中
        List<List<TreeNode>> list = new ArrayList<>();
        //初始化队列，根节点首先放入队列
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        //每一次循环遍历一层，同时将下一层放入队列中
        while (!queue.isEmpty()) {
            //这一层的节点数量
            int levelCount = queue.size();
            List<TreeNode> levelList = new ArrayList<>();
            //根据数量遍历队列
            for (int i = 0; i < levelCount; i++) {
                //根据入队顺序出队（每一层从左到右）
                TreeNode cur = queue.poll();
                if (cur != null) {
                    levelList.add(cur);
                    //下一层按从左到右的顺序放入队列
                    if(cur.left != null) queue.add(cur.left);
                    if(cur.right != null) queue.add(cur.right);
                }
            }
            list.add(levelList);
        }
        return list;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createNode() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        n5.left = n4;
        n4.left = n2;
        n2.left = n1;
        n2.right = n3;
        n5.right = n7;
        n7.left = n6;
        //5 4 2 1 3 7 6
        return n5;
    }

}
