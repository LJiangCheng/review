package com.ljc.alg.designmode.factorymethod;

import com.ljc.alg.designmode.factorymethod.inter.ReaderFactory;

public class JpgReaderFactory implements ReaderFactory {

    @Override
    public JpgImageReader createReader() {
        return null;
    }
}
