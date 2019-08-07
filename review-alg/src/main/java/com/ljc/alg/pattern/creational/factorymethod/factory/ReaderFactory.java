package com.ljc.alg.pattern.creational.factorymethod.factory;

import com.ljc.alg.pattern.creational.factorymethod.inter.ImageReader;

public interface ReaderFactory {

    ImageReader createReader();

}
