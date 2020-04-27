package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

/**
 * 选择排序 O(N^2)  10万-4.8秒
 * 稳定性：稳定
 */
public class SelectSort extends AbstractSort {

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            //遍历过程中记录目标值的下标就行，不用实际交换，直到遍历完成确认极值后再进行一次交换即可
            int temp = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[temp] > arr[j]) {
                    temp = j;
                }
            }
            if (i != temp) {
                swap(arr, i, temp);
            }
        }
    }
}
