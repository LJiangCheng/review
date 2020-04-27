package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

import java.util.Arrays;

/**
 * 不稳定排序
 * 希尔排序（缩减增量排序） O(N^3/2)~O(N^7/6)根据所选用增量序列的不同而有所差异
 * 插入排序的优化版本，可冲破二次时间界。
 * 原理在于每一次比较都可能消除多个逆序，而插入排序系列算法每次比较始终只能消除一个逆序
 */
public class ShellSort extends AbstractSort {

    /**
     * 选用增量序列：2^k-1
     * 对数换底公式：logx(y) = loge(y) / loge(x)
     * java移位运算：n << k 左移k位，相当于n*2^k. 具体到Java计算方式为n*2^(k%32),其最大值为(1 << 31) -1
     */
    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public void sort(int[] arr, int left, int right) {
        //计算增量序列
        int length = (right - left + 1);
        int k = (int) (Math.log(length) / Math.log(2));
        //从增量序列最大值开始运行
        for (; k > 0; k--) {
            int gap = (1 << k) - 1;
            System.out.println("k=" + k + ",arr=" + Arrays.toString(arr));
            int j;
            //对由增量筛选出的子序列进行插入排序
            //错误做法：i以gap为差值自增，对每一个增量只排序一个子序列，结果是 100万 - 212秒 比插入还慢的多，过程中反而多做了很多无用的操作
            //正确做法：i依然要以1为差值自增，关键在于j以gap为差值自减，每一轮都对能分割出的所有子序列排序 1000万 - 4秒 得到了数量级的提升
            for (int i = left + gap; i < right + 1; /*i += gap*/ i++) {
                int temp = arr[i];
                for (j = i; /*j > left*/ j > left + gap && temp < arr[j - gap]; j -= gap) {
                    //后移一个增量
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }
}
























