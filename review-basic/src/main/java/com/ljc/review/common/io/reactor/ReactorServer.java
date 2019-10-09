package com.ljc.review.common.io.reactor;

import java.io.IOException;

public class ReactorServer {

    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(3333);
        new Thread(reactor).start();
    }

}
