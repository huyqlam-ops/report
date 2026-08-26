package com.cloudnative.report.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.cloudnative.report.common.Roles.ADMIN;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain authenticatedPath(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/data/**").hasRole(ADMIN)
                        .anyRequest().permitAll()
                );
        return http.build();
    }

}
