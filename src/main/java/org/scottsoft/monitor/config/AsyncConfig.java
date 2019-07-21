package org.scottsoft.monitor.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    public static final String COLLECTION_EXECUTOR_BEAN_ID = "collectionExecutor";

    private AsyncExceptionHandler asyncExceptionHandler;

    public AsyncConfig(AsyncExceptionHandler asyncExceptionHandler) {
        this.asyncExceptionHandler = asyncExceptionHandler;
    }

    @Bean(name = COLLECTION_EXECUTOR_BEAN_ID)
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("DataCollection");
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }

}
