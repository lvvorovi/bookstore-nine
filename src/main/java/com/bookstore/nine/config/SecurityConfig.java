package com.bookstore.nine.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ACTUATOR_REFRESH = "/actuator/refresh";
    public static final String API_DOCS = "/api-docs/**";
    public static final String SWAGGER_UI = "/swagger-ui/**";

    @NotNull
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwks;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(h -> h
                        .requestMatchers(HttpMethod.GET, SWAGGER_UI, API_DOCS)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, ACTUATOR_REFRESH)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .csrf(c -> c.ignoringRequestMatchers(ACTUATOR_REFRESH))
                .oauth2ResourceServer(r -> r
                        .jwt(j -> j
                                .jwkSetUri(jwks)))
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        var user = User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
