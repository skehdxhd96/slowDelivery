package com.example.slowdelivery;

import com.example.slowdelivery.security.oauth2.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
public class SlowDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlowDeliveryApplication.class, args);
    }

}
