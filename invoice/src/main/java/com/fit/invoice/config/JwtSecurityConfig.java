//package com.fit.invoice.config;
//
//import com.fit.invoice.domain.member.filter.JwtFilter;
//import com.fit.invoice.domain.member.util.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//    private final TokenProvider tokenProvider;
//
//    @Override
//    public void configure(HttpSecurity builder) throws Exception {
//        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
//        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//    }
//}
