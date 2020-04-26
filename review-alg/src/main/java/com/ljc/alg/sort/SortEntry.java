package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.Sort;

import java.util.Arrays;
import java.util.Random;

public class SortEntry {

    public static void main(String[] args) throws InterruptedException {
        //创造随机数组
        int bound = 100;
        int length = 100;
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(bound);
        }
        //System.out.println(Arrays.toString(arr));
        //排序并计时
        Sort sort = new InsertionSort();
        long start = System.currentTimeMillis();
        sort.sort(arr);
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "ms");
        System.out.println(Arrays.toString(arr));
    }

}
