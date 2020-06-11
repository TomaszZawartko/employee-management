package com.tzawartko.employee.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "config.json",
        factory = JsonPropertySourceConfigFactory.class)
@ConfigurationProperties
@Setter
@Getter
public class BusinessConfiguration {
    private Integer maxDirectors;
    private Integer maxSubordinates;
}
