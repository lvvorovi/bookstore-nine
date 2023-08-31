package com.bookstore.eight.config;

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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ACTUATOR_REFRESH = "/actuator/refresh";
    public static final String JS = "/js/**";
    public static final String BOOKS = "/books";
    public static final String HOME = "/";
    public static final String API_DOCS = "/api-docs/**";
    public static final String SWAGGER_UI = "/swagger-ui/**";

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(h -> h
                        .requestMatchers(HttpMethod.GET, SWAGGER_UI, API_DOCS, BOOKS, HOME, JS, ACTUATOR_REFRESH)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, ACTUATOR_REFRESH)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(withDefaults())
                .csrf(c -> c.ignoringRequestMatchers(ACTUATOR_REFRESH))
                .logout(l -> l.logoutSuccessUrl(HOME))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
