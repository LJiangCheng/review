package com.ljc.alg.designmode.factorymethod;

import com.ljc.alg.designmode.factorymethod.inter.ReaderFactory;

public class GifReaderFactory implements ReaderFactory {

    @Override
    public GifImageReader createReader() {
        return null;
    }
}
