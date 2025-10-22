package com.sumukhbhandarkar.honestheart.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/",                 // root
                                "/index.html",
                                "/create-bgv.html",
                                "/request-bgv.html",
                                "/honestheart/**",   // if you serve under this path too
                                "/health",
                                "/actuator/**",
                                "/api/public/**",
                                "/api/consents"      // your consent POST endpoint
                        ).permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable());
        return http.build();
    }
}
