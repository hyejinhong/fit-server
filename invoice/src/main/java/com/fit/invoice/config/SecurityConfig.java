package com.fit.invoice.config;

import com.fit.invoice.domain.member.util.JwtAccessDeniedHandler;
import com.fit.invoice.domain.member.util.JwtAuthenticationEntryPoint;
import com.fit.invoice.domain.member.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF Disabled
                .csrf(AbstractHttpConfigurer::disable)

                // Filter
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // Exception Handling
                .exceptionHandling(configure -> {
                    configure.authenticationEntryPoint(authenticationEntryPoint);
                    configure.accessDeniedHandler(accessDeniedHandler);
                })

                // Session Stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .httpBasic(AbstractHttpConfigurer::disable)

                // 경로별 인가
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/", "/join").permitAll()
                            .requestMatchers("/admin").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })

                // Jwt Filter
//                .apply(new JwtSecurityConfig(jwtProvider))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
