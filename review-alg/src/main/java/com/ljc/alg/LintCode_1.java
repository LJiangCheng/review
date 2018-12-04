package com.ljc.alg;

public class LintCode_1 {

    /**
     * For an array A, if i < j, and A [i] > A [j], called (A [i], A [j]) is a reverse pair.
     * return total of reverse pairs in A.
     */
    public void reversePair(int[] A) {
        //A = [2, 4, 1, 3, 5]
        //使用归并排序，每一次比较之后把大小信息记录下来以减少比较次数 归并排序时间复杂度为O(nlogn)，在此不过是每次比较多加了一次逆序记录而已，不会影响这个时间复杂度
        int reverse = 0;
        mergeAndRecordReverse(A,reverse);
        System.out.println(reverse);
    }

    private void mergeAndRecordReverse(int[] A, int count) {
        if (A.length > 1) {

        }
    }

}
