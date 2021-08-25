package io.github.daedrics.atpco.api.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daedrics.atpco.api.sdk.core.AtpcoApiRestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Redion Muraj
 * @since 28 Jun 2021
 */
@Configuration
@EnableConfigurationProperties(AtpcoConfigurationProperties.class)
@PropertySource({"classpath:base.properties"})
public class AtpcoAutoConfiguration {

    @Autowired
    private AtpcoConfigurationProperties atpcoConfigurationProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public AtpcoApiRestTemplate atpcoApiRestTemplate() {
        return new AtpcoApiRestTemplate(objectMapper, atpcoConfigurationProperties);
    }

}
