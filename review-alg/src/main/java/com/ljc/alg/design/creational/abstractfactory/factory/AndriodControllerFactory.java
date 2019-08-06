package com.ljc.alg.design.creational.abstractfactory.factory;

import com.ljc.alg.design.creational.abstractfactory.impl.AndroidInterfaceController;
import com.ljc.alg.design.creational.abstractfactory.impl.AndroidOperationController;

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
