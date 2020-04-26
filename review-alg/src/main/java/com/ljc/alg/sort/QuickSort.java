package com.ljc.alg.sort;

import com.ljc.alg.sort.inter.AbstractSort;

/**
 * 快排：二分法，不稳定排序 时间复杂度O(NlogN) 空间复杂度O(N)
 * 注：手写的，和书上有一定区别，可继续完善
 */
public class QuickSort extends AbstractSort {

    private InsertionSort insertionSort = new InsertionSort();

    /**
     * 快排驱动程序
     * 选取基准元 --暂缺，直接使用最右侧元素作为基准元
     * 初始化左右边界
     */
    @Override
    public void sort(int[] arr) {
        moreQuickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快排执行程序
     * 估时：1亿-15S 2亿-31S
     * @param arr   数组
     * @param left  序列左界
     * @param right 序列右界
     */
    private void quickSort(int[] arr, int left, int right) {
        //递归终止 改进：当数组元素小于10个时使用插入排序完成剩下的工作
        if (left >= right) {
            return;
        }
        //直接取最右边的元素为基准元 改进：使用三数中值
        int n = arr[right];
        //i从最左，j从基准元前一个开始移动
        int i = left, j = right - 1;
        //遍历交换
        while (i < j) {
            //从右往左，直到小于N时停止；保证右侧全是大于等于N的值
            while (arr[j] >= n && j > left) j--;
            //从左往右，直到大于N时停止；保证左侧全是小于等于N的值
            while (arr[i] <= n && i < right) i++;
            //如果i,j没有交错，则交换所在位置的值
            if (i < j) {
                swap(arr, i, j);
            }
        }
        //需要最后判断一次基准元和i所在元素的大小
        //解释：正常来说，arr[i]<n时，i一定会向前推进。
        //但如果递归到最后arr中只有两个元素的时候，ij初始化的时候就已经相等，根本不会进入循环，此时就需要进行一次如下的比较以防止错误的交换
        //后续优化之后，如果数组长度小于12则使用插入排序，那么此处判断就是不必要的了
        if (arr[i] < n) {
            i++;
        }
        //将基准元放到合适的位置
        swap(arr, right, i);
        //二分法，递归调用
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    /**
     * 快排执行程序优化
     * 估时：1亿-15S 2亿~31S  基本上和上面的只有毫秒级的差距，似乎没有明显的速度提升？
     *
     * @param arr   数组
     * @param left  序列左界
     * @param right 序列右界
     */
    private void moreQuickSort(int[] arr, int left, int right) {
        //递归终止条件
        if (right - left > 10) {
            int n = arr[right];
            int i = left, j = right - 1;
            while (i < j) {
                //换种写法[免得出现重复提示]
                for (; arr[j] >= n && j > left; ) j--;
                for (; arr[i] <= n && i < right; ) i++;
                if (i < j) {
                    swap(arr, i, j);
                }
            }
            //将基准元放到合适的位置
            swap(arr, right, i);
            //二分法，递归调用
            moreQuickSort(arr, left, i - 1);
            moreQuickSort(arr, i + 1, right);
        } else {
            insertionSort.sort(arr, left, right);
        }
    }

}
























