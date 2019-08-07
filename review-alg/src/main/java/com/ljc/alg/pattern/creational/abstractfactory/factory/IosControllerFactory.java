package com.ljc.alg.pattern.creational.abstractfactory.factory;

import com.ljc.alg.pattern.creational.abstractfactory.impl.IosInterfaceController;
import com.ljc.alg.pattern.creational.abstractfactory.inter.OperationController;

public class IosControllerFactory implements ControllerFactory {


    @Override
    public IosInterfaceController createInterfaceController() {
        return null;
    }

    @Override
    public OperationController createOperationController() {
        return null;
    }
}
