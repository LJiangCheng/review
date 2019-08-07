package com.ljc.alg.pattern.creational.abstractfactory.factory;

import com.ljc.alg.pattern.creational.abstractfactory.inter.InterfaceController;
import com.ljc.alg.pattern.creational.abstractfactory.inter.OperationController;

public interface ControllerFactory {

    InterfaceController createInterfaceController();

    OperationController createOperationController();

}
