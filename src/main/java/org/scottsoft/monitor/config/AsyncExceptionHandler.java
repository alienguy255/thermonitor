package org.scottsoft.monitor.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.lang.reflect.Method;
import java.net.SocketTimeoutException;

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        if (ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
            log.warn("Timeout occurred running async task: {}", ex.getMessage());
        } else {
            log.warn("An error occurred running async task.", ex);
        }
    }

}
