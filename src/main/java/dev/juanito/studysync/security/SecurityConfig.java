package dev.juanito.studysync.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Here the cross-site-requests-forgery is disabled and this is for avoid malicious attacks
        http.csrf(csrf -> csrf.disable())
        // Here tyou can add the endpoints you want to be unprotected
        .authorizeHttpRequests(authz -> authz.requestMatchers("/api/users/register", "/api/auth/login").permitAll()
        .anyRequest().authenticated());

        return http.build();
    }

}
