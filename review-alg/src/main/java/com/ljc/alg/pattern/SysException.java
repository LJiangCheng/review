package com.ljc.alg.pattern;

public class SysException extends RuntimeException {

    private SysException(String message) {
        super(message);
    }

    public static SysException instance(String message) {
        return new SysException(message);
    }
}
