package com.ljc.alg;

import org.junit.Test;

public class BulbProblem {

    @Test
    public void bulbStolve() {
        String word = "31mmm值";
        if (word.matches("[0-9a-zA-Z~!@#$%^&*()_+`\\-='.\\\\/,]+")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

}
