package com.ljc.alg.designmode.simplefactory;

import com.ljc.alg.designmode.SysException;

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
            throw SysException.instance("不支持的类型！");
        }
        return shapeDrawing;
    }

}
