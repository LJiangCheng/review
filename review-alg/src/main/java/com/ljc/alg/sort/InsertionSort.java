package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

public class InsertionSort extends AbstractSort {

    /**
     * 插入排序（稳定） O(N^2)
     * 假定0~i的子序列a已经为有序状态，将第i+1个元素从后往前比较直到找到在序列a中的正确位置，这样0~(i+1)的序列就排好序了
     */
    @Override
    public void sort(int[] arr) {
        //从数组的第二个元素开始往前寻找插入点
        for (int i = 1; i < arr.length; i++) {
            //记录第i个元素
            int temp = arr[i];
            //将元素不断向前比较，如果temp小于前面的元素，则将前面的元素后移，temp临时占据该元素的位置。
            //如果temp不小于前面的元素，则说明已经找到正确的位置，temp可确实占据当前位置
            int j;
            for (j = i; j > 0 && temp < arr[j - 1]; j--) {
                //后移一位
                arr[j] = arr[j - 1];
            }
            //插入正确的位置
            arr[j] = temp;
        }
    }
}
