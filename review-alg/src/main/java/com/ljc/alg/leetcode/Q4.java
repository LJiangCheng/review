package com.ljc.alg.leetcode;

import org.junit.Test;

public class Q4 {

    /*给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。*/
    @Test
    public void test() {
        System.out.println(middle(new int[]{3, 8, 23}, new int[]{1, 5, 9, 11}));
    }

    private Double middle(int[] a1, int[] a2) {
        //从两个数组中查找第k个最小值
        int m = a1.length;
        int n = a2.length;
        //二分法达成O(log(m+n))的时间复杂度 每次排除k/2个数并更新k的值


        return 0D;
    }

    private double findk(int[] a1, int start1, int[] a2, int start2, int k) {
        //递归调用
        return 0d;
    }

}
