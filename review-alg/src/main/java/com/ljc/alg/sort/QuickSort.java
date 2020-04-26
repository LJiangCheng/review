package com.ljc.alg.sort;

import java.util.Arrays;

/**
 * 快排
 * 二分法
 * 非稳定算法
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {1, 5, 2, 4, 6, 1, 3, 5, 1, 4, 234, 1231, 11, 9, 23, 423, 423, 4, 5, 214, 4545, 467, 788, 234, 123, 45, 12231, 4564, 13, 4, 1231, 12};
        //int[] arr = {2, 5, 1, 8, 6};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快排驱动程序
     * 选取中间数 --暂缺
     * 初始化左右边界
     */
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快排执行程序
     *
     * @param arr   数组
     * @param left  序列左界
     * @param right 序列右界
     */
    private static void quickSort(int[] arr, int left, int right) {
        //递归终止 改进：当数组元素小于10个时使用插入排序完成剩下的工作
        if (left >= right) {
            return;
        }
        //直接取最右边的元素为基准元 改进：使用三数中值
        int n = arr[right];
        //i从最左，j从基准元前一个开始移动
        int i = left, j = right - 1;
        //遍历交换
        while (i < j) {
            //从右往左，直到小于N时停止；保证右侧全是大于等于N的值
            while (arr[j] >= n && j > left) j--;
            //从左往右，直到大于N时停止；保证左侧全是小于等于N的值
            while (arr[i] <= n && i < right) i++;
            //如果i,j没有交错，则交换所在位置的值
            if (i < j) {
                swap(arr, i, j);
            }
        }
        //需要最后判断一次基准元和i所在元素的大小：i所在元素可能会小于n，但是因为ij已经交错而没有可交换对象？？？
        if (arr[i] < n) {
            i++;
        }
        //将基准元放到合适的位置
        swap(arr, right, i);
        //二分法，递归调用
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    private static void swap(int[] arr, int i, int j) {
        try {
            int a1 = arr[i];
            arr[i] = arr[j];
            arr[j] = a1;
        } catch (RuntimeException e) {
            System.out.println("异常，下标：" + i + "_" + j);
        }
    }

}
























