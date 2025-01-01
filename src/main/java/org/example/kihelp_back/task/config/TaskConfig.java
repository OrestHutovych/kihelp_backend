package org.example.kihelp_back.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class TaskConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}