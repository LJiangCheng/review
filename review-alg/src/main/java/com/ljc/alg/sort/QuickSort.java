package com.ljc.alg.sort;

import java.util.Arrays;

/**
 * 快排
 * 二分法
 * 非稳定算法
 */
public class QuickSort {

    public static void main(String[] args) {
        //int[] arr = {1, 5, 2, 4, 6, 1, 3, 5, 1, 4, 234, 23, 423, 423, 4, 5,214,4545,467,788,234,123,45,12231,4564,13,4,1231,12};
        int[] arr = {5, 2, 1, 6, 8};
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
        //递归终止
        /*if (right - left < 1) {
            return;
        }*/
        if (left > right) {
            return;
        }
        //直接取最右边的元素为中间数
        int n = arr[right];
        int i = left, j = right;
        //遍历交换
        while (i != j) {
            while (arr[j] > n && i < j) j--;
            while (arr[i] < n && i < j) i++;
            if (i < j) {
                swap(arr, i, j);
            }
        }
        /*for (; i <= right - 1; i++) {
            //从左向右遍历，如果遇到大于中间数，等待右侧游标
            if (arr[i] >= n) {
                for (; j > left; j--) {
                    //从右向左遍历，如果遇到小于中间数，交换i、j所在数组元素
                    if (arr[j] <= n) {
                        swap(arr, i, j);
                        break;
                    }
                }
            }
        }*/
        //将原中间值归位
        swap(arr, right, j);
        //二分法，递归调用
        quickSort(arr, left, j - 1);
        quickSort(arr, j + 1, right);
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
























