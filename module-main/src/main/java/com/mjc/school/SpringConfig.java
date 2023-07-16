package com.mjc.school;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mjc.school.controller")
@ComponentScan("com.mjc.school")
public class SpringConfig {
    @Bean
    public AuthorController authorControllerBean() {
        return new AuthorController();
    }

    @Bean
    public NewsController newsControllerBean() {
        return new NewsController();
    }

    @Bean
    public Helper helperBean() {
        return new Helper();
    }
}
