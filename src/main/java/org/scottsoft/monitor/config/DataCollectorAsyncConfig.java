package org.scottsoft.monitor.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class DataCollectorAsyncConfig implements AsyncConfigurer {

    public static final String COLLECTION_EXECUTOR_BEAN_ID = "dataCollectionExecutor";

    @Bean(name = COLLECTION_EXECUTOR_BEAN_ID)
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("DataCollector-");
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            if (ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
                log.warn("Timeout occurred running async task: {}", ex.getMessage());
            } else {
                log.warn("An error occurred running async task.", ex);
            }
        };
    }

}
