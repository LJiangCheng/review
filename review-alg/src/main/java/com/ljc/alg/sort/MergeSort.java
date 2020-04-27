package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

/**
 * 稳定算法
 * 归并 O(NlogN) 1千万-2.2秒 1亿~20秒  比快排略慢，多消耗一份数组空间；
 */
public class MergeSort extends AbstractSort {

    @Override
    public void sort(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    /**
     * 递归分治
     */
    private void mergeSort(int[] arr, int[] temp, int left, int right) {
        //递归，直到分割为一个元素
        if (left < right) {
            int pos = (left + right) / 2;
            mergeSort(arr, temp, left, pos);
            mergeSort(arr, temp, pos + 1, right);
            merge(arr, temp, left, pos, right);
        }
    }

    /**
     * 合并左右数组
     */
    private void merge(int[] arr, int[] temp, int left, int pos, int right) {
        int length = right - left + 1;
        //合并，排序
        int tmpPos = left;
        int rightPos = pos + 1;
        while (left <= pos && rightPos <= right) {
            if (arr[left] <= arr[rightPos]) {
                temp[tmpPos++] = arr[left++];
            } else {
                temp[tmpPos++] = arr[rightPos++];
            }
        }
        while (left <= pos) temp[tmpPos++] = arr[left++];
        while (rightPos <= right) temp[tmpPos++] = arr[rightPos++];
        //copy回原数组
        for (int i = 0; i < length; i++, right--) {
            arr[right] = temp[right];
        }
    }
}



















