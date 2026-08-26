package com.cloudnative.report.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.cloudnative.report.common.Roles.ADMIN;

@Configuration
@RequiredArgsConstructor
public class UserConfiguration {

    private final ServiceUserProperties serviceUserProperties;

    @Bean
    UserDetailsService serviceUser() {
        UserDetails serviceUser = User.withUsername(serviceUserProperties.getUsername())
                .password(serviceUserProperties.getPassword())
                .passwordEncoder(passwordEncoder()::encode)
                .authorities(ADMIN)
                .build();

        return new InMemoryUserDetailsManager(serviceUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
