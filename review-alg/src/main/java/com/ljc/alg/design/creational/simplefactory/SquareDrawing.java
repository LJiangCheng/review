package com.ljc.alg.design.creational.simplefactory;

import com.ljc.alg.design.creational.simplefactory.inter.ShapeDrawing;

public class SquareDrawing implements ShapeDrawing {

    @Override
    public void draw() {
        System.out.println("画方！");
    }

    @Override
    public void erase() {
        System.out.println("擦除方！");
    }
}
