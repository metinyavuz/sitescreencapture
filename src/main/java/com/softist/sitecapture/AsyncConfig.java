/*-----------------------------------------------------------
 * Project : SiteCapture
 * Organization : Softist (http://www.softist.com.tr)
 * Contents :
 *
 *-----------------------------------------------------------
 * Copyright (c) 2018 Softist All Rights Reserved.
 *-----------------------------------------------------------
 * Revision History:
 * who                  when               what
 * Metin Yavuz(CreByM) Sep 10, 2018	Created
 *-----------------------------------------------------------
 */
package com.softist.sitecapture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Metin Yavuz(CreByM)
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${websiteCaptureTaskExecutor.corePoolSize}")
    private int corePoolSize=2;
    @Value("${websiteCaptureTaskExecutor.maxPoolSize}")
    private int maxPoolSize=4;

    @Bean(name = "websiteCaptureTaskExecutor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix("capture-");
        executor.initialize();
        return executor;

    }
}
