package com.cloudnative.report.security.userdetails;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "admin.user")
public class ServiceUserProperties {

    private String password;

    public String getUsername() {
        return "admin";
    }
}
