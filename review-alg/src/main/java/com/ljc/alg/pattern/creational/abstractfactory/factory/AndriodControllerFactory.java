package com.ljc.alg.pattern.creational.abstractfactory.factory;

import com.ljc.alg.pattern.creational.abstractfactory.impl.AndroidInterfaceController;
import com.ljc.alg.pattern.creational.abstractfactory.impl.AndroidOperationController;

public class AndriodControllerFactory implements ControllerFactory {
    @Override
    public AndroidInterfaceController createInterfaceController() {
        return null;
    }

    @Override
    public AndroidOperationController createOperationController() {
        return null;
    }
}
