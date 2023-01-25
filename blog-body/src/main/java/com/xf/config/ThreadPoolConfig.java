package com.xf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 线程池类
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //  0.默认线程名前缀
        executor.setThreadNamePrefix("心烦的博客");
        //  1.核心线程数
        executor.setCorePoolSize(5);
        //  2.最大线程数
        executor.setMaxPoolSize(20);
        //  3.任务队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        //  4.非核心线程最大空闲时间
        executor.setKeepAliveSeconds(60);
        //  5.是否任务结束再关闭线程池？
        executor.setWaitForTasksToCompleteOnShutdown(true);

        //  初始化
        executor.initialize();
        return executor;
    }

}
