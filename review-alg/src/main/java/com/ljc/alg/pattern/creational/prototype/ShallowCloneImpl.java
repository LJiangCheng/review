package com.ljc.alg.pattern.creational.prototype;

import com.ljc.alg.pattern.creational.prototype.abs.ShallowClone;

/**
 * 原型模式：浅克隆
 * 需实现接口Cloneable
 * 子类可继承父类实现的接口
 */
public class ShallowCloneImpl extends ShallowClone {
    public ShallowCloneImpl(String content, Integer num1, int num2) {
        super(content, num1, num2);
    }
}
