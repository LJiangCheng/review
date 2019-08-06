package com.ljc.alg.design;

public class SysException extends RuntimeException {

    private SysException(String message) {
        super(message);
    }

    public static SysException instance(String message) {
        return new SysException(message);
    }
}
