package com.definexpracticum.assignmentthree.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // Declaring the bean for RestTemplate. For autowiring.
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
