package com.bookstore.eight.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "cache")
@Validated
@Data
@RefreshScope
@Slf4j
public class CacheProperties {

    @NotNull
    private Integer ttl;

}
