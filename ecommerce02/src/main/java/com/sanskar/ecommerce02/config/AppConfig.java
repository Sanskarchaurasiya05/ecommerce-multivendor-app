package com.sanskar.ecommerce02.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.sessionManagement(management->management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
                )).authorizeHttpRequests(auth-> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/products/*/reviews").permitAll()
                        .anyRequest().permitAll()
                        // here we addFilters use for validating token which is come from fronted.
//                It adds a custom filter called JwtTokenValidator into the Spring Security filter chain, placing it before the BasicAuthenticationFilter.
//                This ensures that your custom logic to validate JWT tokens runs first, before Spring's built-in authentication logic.
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                        .cors(cors->cors.configurationSource(corsConfigrationSource()));  // use of cors when frontend connect to the backened
        // browser throws the cors exception handle thses exception we use cors , cors configuration will tell spring security that this end point and fronted URL able to access
//        our backend and the methods are available

        return http.build();
    }

    private CorsConfigurationSource corsConfigrationSource(){
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
               CorsConfiguration cfg = new CorsConfiguration();
               cfg.setAllowedOrigins(Collections.singletonList("*"));  // all url can acess our backend and for specific url we use List
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setExposedHeaders(Collections.singletonList("Authorization"));
                cfg.setMaxAge(3600l);
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    when we fetching data using external api then we use rest API::-

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
