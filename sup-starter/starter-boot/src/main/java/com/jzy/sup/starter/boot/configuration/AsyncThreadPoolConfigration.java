package com.jzy.sup.starter.boot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <b>功能：</b>AsyncThreadPoolConfigration<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AsyncThreadPoolProperties.class})
@EnableAsync
public class AsyncThreadPoolConfigration implements AsyncConfigurer{

    @Autowired
    AsyncThreadPoolProperties asyncThreadPoolProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncThreadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncThreadPoolProperties.getMaxPoolSize());
        executor.setKeepAliveSeconds(asyncThreadPoolProperties.getKeepAliveSeconds());
        executor.setQueueCapacity(asyncThreadPoolProperties.getQueueCapacity());
        //替换默认线程池,线程队列满了以后交给调用者执行,也就是同步执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("sup-async-task");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                log.error("Exception message - " + throwable.toString());
                for (StackTraceElement traceElement : throwable.getStackTrace())
                log.error("\tat " + traceElement);
                for (Object param : objects) {
                    log.error("Parameter value - " + param);
                }
            }
        };
    }
}
