package com.ljc.alg.design.creational.abstractfactory.factory;

import com.ljc.alg.design.creational.abstractfactory.impl.IosInterfaceController;
import com.ljc.alg.design.creational.abstractfactory.inter.OperationController;

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
