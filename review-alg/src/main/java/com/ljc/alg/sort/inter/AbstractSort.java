package com.ljc.alg.sort.inter;

public abstract class AbstractSort implements Sort {

    /**
     * 交换元素
     */
    protected void swap(int[] arr, int i, int j) {
        try {
            int a1 = arr[i];
            arr[i] = arr[j];
            arr[j] = a1;
        } catch (RuntimeException e) {
            System.out.println("异常，下标：" + i + "_" + j);
        }
    }

}
