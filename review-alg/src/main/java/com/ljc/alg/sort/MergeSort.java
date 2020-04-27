package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 稳定算法
 * 归并 O(NlogN) 1千万-2.2秒 1亿~20秒  比快排略慢，多消耗一份数组空间；
 */
public class MergeSort extends AbstractSort {

    @Override
    public void sort(int[] arr) {
        /*int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);*/
        try {
            mergeSort(arr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用线程池
     */
    private void mergeSort(int[] arr) throws InterruptedException {
        int tNum = Runtime.getRuntime().availableProcessors() + 1;
        CountDownLatch latch = new CountDownLatch(tNum);
        ExecutorService pool = Executors.newFixedThreadPool(tNum);
        int gap = arr.length / tNum;
        for (int i = 0; i <= tNum; i++) {
            final int j = i;
            pool.submit(() -> {
                int left = j * gap;
                int right = (j + 1) * gap - 1;
                int maxIndex = arr.length - 1;
                right = right > maxIndex ? maxIndex : right;
                int[] temp = new int[arr.length];
                mergeSort(arr, temp, left, right);
                latch.countDown();
            });
        }
        latch.await();
        mergeTimes(arr, tNum);
        pool.shutdown();
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
    private void merge(int[] arr, int[] temp, int left, int leftEnd, int right) {
        int length = right - left + 1;
        //合并，排序
        int tmpPos = left;
        int rightPos = leftEnd + 1;
        while (left <= leftEnd && rightPos <= right) {
            if (arr[left] <= arr[rightPos]) {
                temp[tmpPos++] = arr[left++];
            } else {
                temp[tmpPos++] = arr[rightPos++];
            }
        }
        while (left <= leftEnd) temp[tmpPos++] = arr[left++];
        while (rightPos <= right) temp[tmpPos++] = arr[rightPos++];
        //copy回原数组
        for (int i = 0; i < length; i++, right--) {
            arr[right] = temp[right];
        }
    }

    /**
     * 借助上面的程序手动合并数组
     */
    private void mergeTimes(int[] arr, int tNum) {
        int[] temp = new int[arr.length];
        int gap = arr.length / tNum;
        while (tNum > 1) {
            for (int i = 0; i < tNum - 1; i += 2) {
                int left = i * gap;
                int leftEnd = (i + 1) * gap;
                int right = (i + 2) * gap;
                int maxIndex = arr.length - 1;
                right = right > maxIndex ? maxIndex : right;
                System.out.println(left + "-" + leftEnd + "-" + right);
                merge(arr, temp, left, leftEnd, right);
            }
            gap = gap * 2;
            tNum = (int) Math.ceil((double) tNum / 2);
        }
    }
}



















