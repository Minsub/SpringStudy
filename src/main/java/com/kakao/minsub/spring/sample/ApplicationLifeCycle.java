package com.kakao.minsub.spring.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ApplicationLifeCycle implements SmartLifecycle {
    private final Logger logger = LoggerFactory.getLogger(ApplicationLifeCycle.class);
    
    @Override
    public boolean isAutoStartup() {
        return false;
    }
    
    @Override
    public void stop(Runnable callback) {
        logger.info("[GRAY_TEST] ApplicationLifeCycle: stop. {}", callback);
        try {
            TimeUnit.SECONDS.sleep(10);
            callback.run();
        } catch (final InterruptedException e) {
            logger.error("[GRAY_TEST] ApplicationLifeCycle ERROR", e);
        }
    }
    
    @Override
    public void start() {
        logger.info("[GRAY_TEST] ApplicationLifeCycle: start");
    }
    
    @Override
    public void stop() {
        logger.info("[GRAY_TEST] ApplicationLifeCycle: stop");
    }
    
    @Override
    public boolean isRunning() {
        return false;
    }
    
    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public String toString() {
        return "** ApplicationLifeCycle **";
    }
}
