package com.ljc.review.common.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LambdaExpre {

    @Test
    public void test14() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 可改变对象
        list.stream().map((i) -> i * 3).forEach(System.out::println);

        // 不可改变list中的原有对象
        list.forEach(i -> i = i * 3);
        list.forEach(System.out::println);
    }

    @Test
    public void test() {
        List<Object> list = new ArrayList<>();
        for (Object o : list) {
            System.out.println(o);
        }
    }

}
