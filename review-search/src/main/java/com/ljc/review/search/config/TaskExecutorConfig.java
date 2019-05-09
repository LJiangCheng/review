package com.ljc.review.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Value("${task.core_pool_size}")
    private Integer corePoolSize;
    @Value("${task.max_pool_size}")
    private Integer maxPoolSize;
    @Value("${task.queue_capacity}")
    private Integer queueCapacity;
    @Value("${task.keep_alive_seconds}")
    private Integer keepAliveSeconds;

    @Bean(name="taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        return taskExecutor;
    }

}
