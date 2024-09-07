package com.fit.invoice.domain.member.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.invoice.global.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(401);

        BaseResponse<String> baseResponse = new BaseResponse<>("99", "로그인 하세요.", authException.getMessage());
        response.setContentType("application/json");

        try (BufferedOutputStream bo = new BufferedOutputStream(response.getOutputStream())) {
            bo.write(objectMapper.writeValueAsString(baseResponse).getBytes());
        }
        response.flushBuffer();
    }
}
