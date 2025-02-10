package com.bookcharm.app;

import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public SpringPhysicalNamingStrategy springPhysicalNamingStrategy() {
        return new CustomPhysicalNamingStrategy();
    }
}
