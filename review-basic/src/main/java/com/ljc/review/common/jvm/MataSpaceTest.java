package com.ljc.review.common.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class MataSpaceTest {

    public static void main(String[] args) {
        try {
            int i = 0;
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(MataSpaceTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, args));
                enhancer.create();
                System.out.println(i++);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
