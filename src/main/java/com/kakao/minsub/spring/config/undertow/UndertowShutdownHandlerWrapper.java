package com.kakao.minsub.spring.config.undertow;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;

public class UndertowShutdownHandlerWrapper implements HandlerWrapper {
    private GracefulShutdownHandler gracefulShutdownHandler;
    
    @Override
    public HttpHandler wrap(HttpHandler handler) {
        if (gracefulShutdownHandler == null) {
            this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
        return gracefulShutdownHandler;
    }
    
    public GracefulShutdownHandler getGracefulShutdownHandler() { return gracefulShutdownHandler; }
}
