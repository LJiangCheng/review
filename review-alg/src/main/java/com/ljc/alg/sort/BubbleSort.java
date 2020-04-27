package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

/**
 * 冒泡排序（稳定） O(N^2)  10万 - 20秒
 * 理论上和插入排序的时间上界一样，但是实际速度要慢得多，因为冒泡比插入所需要的元素交换次数多很多
 */
public class BubbleSort extends AbstractSort {

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                //比较相邻的元素，如果前面的大于后面的则交换。如此则每一轮循环会将最大值找出来并放到最后
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
}
