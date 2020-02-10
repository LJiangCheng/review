package com.ljc.review.common.concurrent.inpratice.章15原子变量与非阻塞同步机制.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS来维持包含多个变量的不变性条件
 * 设置数值得上下界，更新的时候要保证变量之间的相对关系不变
 */
public class CasNumberRange {
    private AtomicReference<IntPair> value = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return value.get().lower;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldPair = value.get();
            if (i > oldPair.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }
            IntPair newPair = new IntPair(i, oldPair.upper);
            //比较并更新
            if (value.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }

    public int getUpper() {
        return value.get().upper;
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldPair = value.get();
            if (i < oldPair.lower) {
                throw new IllegalArgumentException("Can't set lower to " + i + " < lower");
            }
            IntPair newPair = new IntPair(oldPair.lower, i);
            //比较并更新
            if (value.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }

    private static class IntPair {
        final int lower;   //不变性条件：lower <= upper
        final int upper;

        IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

}















