package com.example.feignconsumer.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 关闭Hystrix配置类
 */
@Configuration
public class DisableHystrixConfiguration {

    @Bean
    public Feign.Builder feignBuild() {
        return Feign.builder();
    }
}
