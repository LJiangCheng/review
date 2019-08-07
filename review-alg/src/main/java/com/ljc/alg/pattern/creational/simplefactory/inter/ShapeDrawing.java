package com.ljc.alg.pattern.creational.simplefactory.inter;

import com.ljc.alg.pattern.SysException;
import com.ljc.alg.pattern.creational.simplefactory.RoundDrawing;
import com.ljc.alg.pattern.creational.simplefactory.SquareDrawing;

public interface ShapeDrawing {

    void draw();

    void erase();

    static ShapeDrawing create(String drawingName) {
        ShapeDrawing shapeDrawing;
        if ("round".equals(drawingName)) {
            shapeDrawing = new RoundDrawing();
        } else if ("square".equals(drawingName)) {
            shapeDrawing = new SquareDrawing();
        } else {
            throw SysException.instance("不支持的类型:" + drawingName);
        }
        return shapeDrawing;
    }

}
