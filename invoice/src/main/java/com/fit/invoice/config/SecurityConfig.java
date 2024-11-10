package com.fit.invoice.config;

import com.fit.invoice.domain.mail.service.EmailService;
import com.fit.invoice.domain.member.filter.JwtFilter;
import com.fit.invoice.domain.member.filter.LoginFilter;
import com.fit.invoice.domain.member.service.CustomUserDetailsService;
import com.fit.invoice.domain.member.util.JwtAccessDeniedHandler;
import com.fit.invoice.domain.member.util.JwtAuthenticationEntryPoint;
import com.fit.invoice.domain.member.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider());

        return http
                .httpBasic(AbstractHttpConfigurer::disable)

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
                .authorizeHttpRequests(requests -> { requests
                        .requestMatchers("/login", "/api/v1/verify").permitAll()
                        .requestMatchers("/api/v1/invoices/{invoiceId}").permitAll() // DEMO용 임시 추가
                        .requestMatchers(HttpMethod.POST, "/api/v1/members").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                        .anyRequest().authenticated();
                })
                .addFilterAt(new LoginFilter(authenticationConfiguration.getAuthenticationManager(), emailService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtProvider), LoginFilter.class)

                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
