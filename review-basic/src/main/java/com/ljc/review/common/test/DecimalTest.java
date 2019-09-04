package com.ljc.review.common.test;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Objects;

public class DecimalTest {

    @Test
    public void test() {
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println(df.format(0.2551));
        System.out.println(df.format(-2.3512).equals(df.format(-2.35)));
        System.out.println(df.format(234.2324));
        System.out.println(Objects.equals(1.23, 1.23));
        System.out.println(Double.doubleToLongBits(0.1111111));
    }

}
