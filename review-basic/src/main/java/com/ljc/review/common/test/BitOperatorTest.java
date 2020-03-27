package com.ljc.review.common.test;

import org.junit.Test;

public class BitOperatorTest {

    @Test
    public void test() {
        int i = 10;
        System.out.println(1 << 30);
        System.out.println((10 << (32 - 16)) - 1);
    }

}
