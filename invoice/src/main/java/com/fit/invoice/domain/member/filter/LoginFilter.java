package com.fit.invoice.domain.member.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.invoice.domain.mail.service.EmailService;
import com.fit.invoice.domain.member.dto.CustomUserDetails;
import com.fit.invoice.domain.member.exception.MemberException;
import com.fit.invoice.domain.member.exception.MemberExceptionType;
import com.fit.invoice.domain.member.util.JwtProvider;
import com.fit.invoice.global.dto.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("### 로그인 시도");

        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JsonNode jsonNode = objectMapper.readTree(requestBody);

            String username = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new MemberException(MemberExceptionType.INTERNAL_ERROR);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // 메일 전송
        emailService.sendMail(username);

        BaseResponse<Void> baseResponse = new BaseResponse<>("00", "메일로 발송된 인증코드를 확인하세요.", null);
        try (BufferedOutputStream bo = new BufferedOutputStream(response.getOutputStream())) {
            bo.write(objectMapper.writeValueAsString(baseResponse).getBytes());
        }
        response.setContentType("application/json");
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);

        BaseResponse<String> baseResponse = new BaseResponse<>("99", "로그인에 실패했습니다. 계정을 확인하세요.", failed.getMessage());
        response.setContentType("application/json");

        try (BufferedOutputStream bo = new BufferedOutputStream(response.getOutputStream())) {
            bo.write(objectMapper.writeValueAsString(baseResponse).getBytes());
        }
        response.flushBuffer();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }
}
