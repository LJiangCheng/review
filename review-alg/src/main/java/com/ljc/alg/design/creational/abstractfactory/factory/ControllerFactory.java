package com.ljc.alg.design.creational.abstractfactory.factory;

import com.ljc.alg.design.creational.abstractfactory.inter.InterfaceController;
import com.ljc.alg.design.creational.abstractfactory.inter.OperationController;

public interface ControllerFactory {

    InterfaceController createInterfaceController();

    OperationController createOperationController();

}
