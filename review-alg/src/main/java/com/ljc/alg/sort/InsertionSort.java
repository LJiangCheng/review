package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

/**
 * O(N^2)的三种排序描述
 * 插入：从后往前插入寻找正确位置
 * 选择：从第一个位置开始从前往后寻找适合对应位置的值（最大或最小）
 * 冒泡：从前往后不断比较并交换相邻元素，每一轮都将大值往下沉，小值往上浮（如果每一次比较只记录下标不进行实际交换就成了选择排序）
 * <p>
 * 实践速度：
 * 快排（不稳定） >> 希尔（不稳定） >> 插入 > 选择 >> 冒泡
 * 原因：
 * 插入排序最大程度利用了已排好序的有序队列，到一定程度就可终止内循环；
 * 选择每一次内循环都要遍历到最后；
 * 冒泡同样如此，而且交换次数更加多
 */
public class InsertionSort extends AbstractSort {

    /**
     * 插入排序（稳定） O(N^2) 10万-1.48秒 100万-116秒 基本上是按二次的时间递增
     * 假定0~i的子序列a已经为有序状态，将第i+1个元素从后往前比较直到找到在序列a中的正确位置，这样0~(i+1)的序列就排好序了
     */
    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    /**
     * 可指定要排序的子序列
     */
    public void sort(int[] arr, int left, int right) {
        int j;
        //从数组的第二个元素开始往前寻找插入点
        for (int i = left + 1; i < right + 1; i++) {
            //记录第i个元素
            int temp = arr[i];
            //将元素不断向前比较，如果temp小于前面的元素，则将前面的元素后移，temp临时占据该元素的位置。
            //如果temp不小于前面的元素，则说明已经找到正确的位置，temp可确实占据当前位置
            for (j = i; j > left && temp < arr[j - 1]; j--) {
                //后移一位
                arr[j] = arr[j - 1];
            }
            //插入正确的位置
            arr[j] = temp;
        }
    }
}













