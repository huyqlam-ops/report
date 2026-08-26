package com.cloudnative.report.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.cloudnative.report.common.Roles.ADMIN;

@Configuration
@RequiredArgsConstructor
public class UserConfiguration {

    private final ServiceUserProperties serviceUserProperties;

    @Bean
    UserDetailsService serviceUser() {
        UserDetails serviceUser = User.builder()
                .username(serviceUserProperties.getUsername())
                .password(serviceUserProperties.getPassword())
                .roles(ADMIN)
                .build();

        return new InMemoryUserDetailsManager(serviceUser);
    }
}
