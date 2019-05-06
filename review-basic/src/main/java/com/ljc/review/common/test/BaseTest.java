package com.ljc.review.common.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BaseTest {

    public static void main(String[] args) {
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            //throw e;
        }
        System.out.println("aaa");
    }

    @Test
    public void t1() {
        Double i = 4.9999;
        Double j = 5.00;
        System.out.println((int)(i/j));
    }

    @Test
    public void t2() {
        BigDecimal i = new BigDecimal("1.4");
        BigDecimal j = new BigDecimal("1.2");
        System.out.println(i.multiply(j));
    }

}
