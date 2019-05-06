package com.ljc.test;

import com.ljc.util.EhCacheUtil;
import org.junit.Test;

public class MyTest {

    @Test
    public void test1() throws InterruptedException {
        EhCacheUtil.put("cache", "test", "ttt", 2);
        System.out.println(EhCacheUtil.get("cache", "test"));
        Thread.sleep(1800);
        System.out.println(EhCacheUtil.get("cache", "test"));
    }

}
