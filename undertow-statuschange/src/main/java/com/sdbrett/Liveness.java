package com.sdbrett;


import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.AllowedMethodsHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

import java.util.concurrent.atomic.AtomicBoolean;

public class Liveness {

    public AtomicBoolean allGoodInTheHood = new AtomicBoolean(false);

    public Liveness(int port) {
        PathHandler handler = new PathHandler(new ResponseCodeHandler(404));
        handler.addExactPath("/liveness", new AllowedMethodsHandler(exchange -> {
            if (allGoodInTheHood.get()) {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.getResponseSender().send("Hello");
            }
            else {
                new ResponseCodeHandler(500).handleRequest(exchange);
            }

        }, Methods.GET));

        Undertow server = Undertow.builder().addHttpListener(port, "0.0.0.0").setHandler(handler).build();
        server.start();
    }

}