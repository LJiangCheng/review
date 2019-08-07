package com.ljc.alg.pattern.creational.simplefactory;

import com.ljc.alg.pattern.creational.simplefactory.inter.ShapeDrawing;

public class RoundDrawing implements ShapeDrawing {

    @Override
    public void draw() {
        System.out.println("画圆！");
    }

    @Override
    public void erase() {
        System.out.println("擦除圆！");
    }
}
